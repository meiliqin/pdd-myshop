package com.ht.weichat.service.impl;

import com.ht.weichat.mapper.TbDateSalesMapper;
import com.ht.weichat.mapper.TbKeyValueMapper;
import com.ht.weichat.mapper.TbStockMapper;
import com.ht.weichat.pojo.TbDateSales;
import com.ht.weichat.pojo.TbKeyValue;
import com.ht.weichat.pojo.TbStock;
import com.ht.weichat.service.*;
import com.ht.weichat.utils.ConstantPool;
import com.ht.weichat.utils.GlobalUtils;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddGoodsListGetRequest;
import com.pdd.pop.sdk.http.api.response.PddGoodsListGetResponse;
import com.pdd.pop.sdk.http.api.response.PddOrderListGetResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ht.weichat.utils.ConstantPool.clientId;
import static com.ht.weichat.utils.ConstantPool.clientSecret;
import static com.ht.weichat.utils.GlobalUtils.getDateBefore;


@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private TbStockMapper tbStockMapper;
    public static final String clientId = "864a62d925204fa29d5a2d2b22d8c67e";
    public static final String clientSecret = "f821108d75c0dc7951e94cdd27103a28d19c7839";
    private Logger logger = Logger.getLogger("StockServiceImpl");

    @Autowired
    private TbKeyValueMapper tbKeyValueMapper;

    @Autowired
    private TbDateSalesMapper tbDateSalesMapper;

    @Autowired
    private SalesService salesService;

    @Override
    public void insert(TbStock tbStock) throws Exception {
        Date currentDate = new Date();
        //tbStock.setSkuUrl(null);
        tbStock.setCreatTime(currentDate);
        tbStock.setUpdatTime(currentDate);

        tbStockMapper.insert(tbStock);


    }


    @Override
    public StockResult queryStockResult() {
        StockResult stockResult = getGoodInfo();
        //todo 这个表中可去掉除 goodid skuid stock之外的无关字段
        if (stockResult != null) {
            List<StockResult.GoodItem> goodStockList = stockResult.goodStockList;
            try {
                List<String> skuList = new ArrayList<>();
                for (StockResult.GoodItem goodItem : goodStockList) {
                    for (StockResult.SkuItem skuItem : goodItem.sku_list) {
                        skuList.add(String.valueOf(skuItem.sku_id));
                    }
                }
                //批量查询 会快很多
                List<TbStock> tbStockList = queryStockList(skuList);
                Map<String, TbStock> tbStockMap = new HashMap<>();
                for (TbStock tbStock : tbStockList) {
                    tbStockMap.put(tbStock.getSkuId(), tbStock);
                }

                for (StockResult.GoodItem goodItem : goodStockList) {
                    for (StockResult.SkuItem skuItem : goodItem.sku_list) {
                        TbStock tbStock = tbStockMap.get(String.valueOf(skuItem.sku_id));
                        if (tbStock != null) {
                            skuItem.setSku_stock_quantity(tbStock.getStock());
                            logger.info("tbStock skuId:" + tbStock.getSkuId() + "skuItem skuId:" + skuItem.sku_id + "查询到库存：" + tbStock.getStock());
                        } else {
                            TbStock tbStock1 = new TbStock();
                            tbStock1.setSkuId(String.valueOf(skuItem.sku_id));
                            tbStock1.setGoodId(String.valueOf(goodItem.goods_id));
                            tbStock1.setGoodName(goodItem.goods_name);
                            tbStock1.setThumbUrl(goodItem.thumb_url);
                            tbStock1.setSkuSpec(skuItem.spec);
                            insert(tbStock1);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stockResult;
    }

    @Override
    public void saveStock(List<TbStock> tbStockList) {
        updateStockList(tbStockList);
        String yesterdayDate = getDateBefore(1);
        TbKeyValue keyValue = new TbKeyValue();
        keyValue.setInfoKey(ConstantPool.Key_SyncSalesDate);
        keyValue.setInfoValue(yesterdayDate);
        if (null == tbKeyValueMapper.selectByPrimaryKey(ConstantPool.Key_SyncSalesDate)) {
            tbKeyValueMapper.insert(keyValue);
        } else {
            tbKeyValueMapper.updateByPrimaryKey(keyValue);
        }

    }

    @Override
    public List<TbStock> queryStockList(List<String> skuIdList) {
        return tbStockMapper.selectStocksByPrimaryKey(skuIdList);
    }


    @Override
    public void updateStockList(List<TbStock> tbStockList) {
        tbStockMapper.updateStockQuantityList(tbStockList);
    }

    public String getCurAccessToken() {
        if (GlobalUtils.accessToken == null) {
            TbKeyValue tbKeyValue = tbKeyValueMapper.selectByPrimaryKey(ConstantPool.Key_AccessToken);
            GlobalUtils.accessToken = tbKeyValue.getInfoValue();
            logger.info("获取到数据库的值accessToken：" + GlobalUtils.accessToken);
        }
        return GlobalUtils.accessToken;
    }

    @Override
    public StockResult getGoodInfo() {
        PopClient client = new PopHttpClient(clientId, clientSecret);
        PddGoodsListGetRequest request = new PddGoodsListGetRequest();
        try {
            PddGoodsListGetResponse response = client.syncInvoke(request, getCurAccessToken());
            List<PddGoodsListGetResponse.GoodsListGetResponseGoodsListItem> goodsList = response.getGoodsListGetResponse().getGoodsList();
            StockResult stockResult = new StockResult();
            for (PddGoodsListGetResponse.GoodsListGetResponseGoodsListItem goodsListItem : goodsList) {
                StockResult.GoodItem goodItem = new StockResult.GoodItem();
                goodItem.goods_id = goodsListItem.getGoodsId();
                goodItem.goods_name = goodsListItem.getGoodsName();
                goodItem.thumb_url = goodsListItem.getThumbUrl();
                List<PddGoodsListGetResponse.GoodsListGetResponseGoodsListItemSkuListItem> skuListItems = goodsListItem.getSkuList();

                for (PddGoodsListGetResponse.GoodsListGetResponseGoodsListItemSkuListItem skuListItem : skuListItems) {
                    StockResult.SkuItem skuItem = new StockResult.SkuItem();
                    skuItem.sku_id = skuListItem.getSkuId();
                    skuItem.spec = skuListItem.getSpec();
                    skuItem.sku_stock_quantity = 0;
                    goodItem.sku_list.add(skuItem);
                }
                goodItem.good_stock_quantity = 0;
                stockResult.goodStockList.add(goodItem);
            }
            logger.info("stock:" + stockResult.goodStockList.size());
            return stockResult;

//            for (PddGoodsListGetResponse.GoodsListGetResponseGoodsListItem goodsListItem : goodsList) {
//                List<PddGoodsListGetResponse.GoodsListGetResponseGoodsListItemSkuListItem> skuListItems = goodsListItem.getSkuList();
//                for (PddGoodsListGetResponse.GoodsListGetResponseGoodsListItemSkuListItem skuListItem : skuListItems) {
//                    TbStock tbStock = new TbStock();
//                    tbStock.setGoodId(String.valueOf(goodsListItem.getGoodsId()));
//                    tbStock.setGoodName(goodsListItem.getGoodsName());
//                    tbStock.setThumbUrl(goodsListItem.getThumbUrl());
//                    tbStock.setSkuId(String.valueOf(skuListItem.getSkuId()));
//                    tbStock.setSkuSpec(skuListItem.getSpec());
//                    tbStock.setStock(0);
//                    save(tbStock);
//                }
//            }

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    @Override
    public StockResult syncSales(StockResult stockResult) {
        TbKeyValue tbKeyValue = tbKeyValueMapper.selectByPrimaryKey(ConstantPool.Key_SyncSalesDate);
        if(tbKeyValue==null){
            return stockResult;
        }
        String lastDate = tbKeyValue.getInfoValue();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date last = simpleDateFormat.parse(lastDate);//假设每天操作一次 上次保存的昨天2020-2-7
            Date cur = new Date();//今天 2020-2-9
            int daydiff = GlobalUtils.getDayDiffer(last, cur)-1;
            for (int i = 1; i <=daydiff; i++) {
                String date=getDateBefore(i);
                logger.info("正在减去销量："+date);
                TbDateSales dateSales = tbDateSalesMapper.selectByPrimaryKey(date);
                SaleResult daySaleResult = null;
                if (dateSales != null) {
                    daySaleResult = JsonUtil.transferToObj(dateSales.getJsonSalesResult(), SaleResult.class);
                } else {
                    daySaleResult = salesService.getDateSales(date);
                }
                Map<String,Integer> salesMap=new HashMap<>();
                for(SaleResult.GoodItem saleGoodItem:daySaleResult.daySale){
                    for(SaleResult.SkuItem saleSkuItem:saleGoodItem.sku_list){
                        salesMap.put(saleSkuItem.sku_id,saleSkuItem.sale_count);
                    }
                }
                for(StockResult.GoodItem goodItem:stockResult.goodStockList){
                   for(StockResult.SkuItem skuItem:goodItem.sku_list){
                       int salesCount=salesMap.get(String.valueOf(skuItem.sku_id));
                       skuItem.setSku_stock_quantity(skuItem.sku_stock_quantity-salesCount);
                   }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stockResult;
    }



}
