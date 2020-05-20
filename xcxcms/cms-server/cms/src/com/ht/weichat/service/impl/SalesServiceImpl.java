package com.ht.weichat.service.impl;

import com.ht.weichat.mapper.TbDateSalesMapper;
import com.ht.weichat.mapper.TbKeyValueMapper;
import com.ht.weichat.pojo.TbDateSales;
import com.ht.weichat.pojo.TbKeyValue;
import com.ht.weichat.service.*;
import com.ht.weichat.utils.ConstantPool;
import com.ht.weichat.utils.GlobalUtils;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddOrderListGetRequest;
import com.pdd.pop.sdk.http.api.response.PddOrderListGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.ht.weichat.utils.ConstantPool.*;
import static com.ht.weichat.utils.GlobalUtils.getDateBefore;


@Service
public class SalesServiceImpl implements SalesService {
    public static final int pageSize = 100;
    public static final String daytimeStart = "00:00:00";
    public static final String daytimeEnd = "23:59:59";
    //    public static String accessToken = "14bc0edea9da4da2bc47ebb7f0aeb6c8f9052bb0";
//    public static final String saleDir = "/users/meiliqin/project/pdd-myshop/xcxcms/sales/";
    private Logger logger = Logger.getLogger("SalesServiceImpl");

    @Autowired
    private TbDateSalesMapper tbDateSalesMapper;

    @Autowired
    private TbKeyValueMapper tbKeyValueMapper;

    @Override
    public String getCodeUrl() {
        String url = "http://mms.pinduoduo.com/open.html";
        //client_id
        url += "?client_id=" + clientId;
        //授权类型为CODE
        url += "&response_type=code";
        //授权回调地址
        url += "&redirect_uri=" + redirectUri;
        return url;
    }

    @Override
    public String getCurAccessToken() {
        if(GlobalUtils.accessToken==null){
            TbKeyValue tbKeyValue=tbKeyValueMapper.selectByPrimaryKey(ConstantPool.Key_AccessToken);
            GlobalUtils.accessToken=tbKeyValue.getInfoValue();
            logger.info("获取到数据库的值accessToken："+GlobalUtils.accessToken);
        }
        return GlobalUtils.accessToken;
    }

    @Override
    public String getAccessTokenFromCode(String code) {

        try {
            PopAccessTokenClient client = new PopAccessTokenClient(
                    clientId,
                    clientSecret);
            AccessTokenResponse accessTokenResponse = client.generate(code);
            String accessToken = accessTokenResponse.getAccessToken();
            logger.info("accesstoken:" + accessToken);
            TbKeyValue keyValue=new TbKeyValue();
            keyValue.setInfoKey(ConstantPool.Key_AccessToken);
            keyValue.setInfoValue(accessToken);

           if (null==tbKeyValueMapper.selectByPrimaryKey(ConstantPool.Key_AccessToken)){
               tbKeyValueMapper.insert(keyValue);
               logger.info("accesstoken已保存数据库");
           }else {
               tbKeyValueMapper.updateByPrimaryKey(keyValue);
               logger.info("accesstoken已更新数据库");
           }

            GlobalUtils.accessToken=accessToken;
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SaleResult unsend() {
        Date today = new Date();
        SaleResult saleResult = new SaleResult(today.toLocaleString(), UN_SEND_DAY+"天未发货销量数据");
        PopClient client = new PopHttpClient(clientId, clientSecret);
        int dayIndex = 1;
        long startTime = today.getTime() / 1000L - 86400 * dayIndex;
        long endTime = today.getTime() / 1000L;
        //最多统计未发货,天数
        while (dayIndex <= UN_SEND_DAY) {
            PddOrderListGetResponse firstResponse = getOrderListGetResponse(client, 1, startTime, endTime, 1);
            if (firstResponse == null) {
                logger.info("获取数据失败");
                break;
            }
            if (firstResponse.getOrderListGetResponse() == null) {
                if (firstResponse.getErrorResponse() != null) {
                    logger.info(firstResponse.getErrorResponse().getErrorMsg());
                }
                logger.info("获取订单失败");
                break;
            }
            List<PddOrderListGetResponse.OrderListGetResponseOrderListItem> firstOrderList = firstResponse.getOrderListGetResponse().getOrderList();
            int totalCount = firstResponse.getOrderListGetResponse().getTotalCount();
            logger.info("正在统计未发货订单:" + "第" + dayIndex + "天" + "：订单数" + totalCount);

//            if(totalCount<=0){
//                break;
//            }
            saleResult.total_order_count += totalCount;
            calOrderList(firstOrderList, saleResult);
            if (totalCount > firstOrderList.size()) {
                int page = totalCount % pageSize > 0 ? totalCount / pageSize + 1 : totalCount / pageSize;
                for (int i = 2; i <= page; i++) {
                    PddOrderListGetResponse response = getOrderListGetResponse(client, 1, startTime, endTime, i);
                    List<PddOrderListGetResponse.OrderListGetResponseOrderListItem> orderList = response.getOrderListGetResponse().getOrderList();
                    calOrderList(orderList, saleResult);
                }
            }
            dayIndex++;
            startTime = startTime - 86400;
            endTime = endTime - 86400;
        }

        Collections.sort(saleResult.daySale, new GoodComparator());
        for (SaleResult.GoodItem goodItem : saleResult.daySale) {
            Collections.sort(goodItem.sku_list, new SkuComparator());
        }

//        String result = JsonUtil.transferToJson(saleResult);
        // writeDateToFile(today.toLocaleString()+"未发货",result);
        return saleResult;
    }

    @Override
    public SaleResult week() {
        SaleResult weekSalesResult = new SaleResult(getDateBefore(0), "周销量");
        for (int i = 1; i <= 7; i++) {
            String date = getDateBefore(i);
            SaleResult daySaleResult = null;
            TbDateSales dateSales = tbDateSalesMapper.selectByPrimaryKey(date);
            if (dateSales != null) {
                logger.info("在数据库找到前" + i + "天数据");
                daySaleResult = JsonUtil.transferToObj(dateSales.getJsonSalesResult(), SaleResult.class);
            } else {
                daySaleResult = getDateSales(date);
            }
            weekSalesResult.total_order_count+=daySaleResult.total_order_count;
            weekSalesResult.pay_amount+=daySaleResult.pay_amount;
            for(SaleResult.GoodItem dayGoodItem:daySaleResult.daySale){
                int findGoodIndex = findGoodItemInList(dayGoodItem.goods_id, weekSalesResult.daySale);
                if(findGoodIndex<0){
                    SaleResult.GoodItem weekGoodItem = new SaleResult.GoodItem();
                    weekGoodItem.goods_id = dayGoodItem.goods_id;
                    weekGoodItem.goods_name = dayGoodItem.goods_name;
                    weekGoodItem.pay_amount += dayGoodItem.pay_amount;
                    weekGoodItem.sale_count += dayGoodItem.sale_count;
                    weekGoodItem.sku_list.addAll(dayGoodItem.sku_list);
                    weekSalesResult.daySale.add(weekGoodItem);
                }else {
                    SaleResult.GoodItem weekGoodItem=weekSalesResult.daySale.get(findGoodIndex);
                    weekGoodItem.pay_amount += dayGoodItem.pay_amount;
                    weekGoodItem.sale_count += dayGoodItem.sale_count;
                    for(SaleResult.SkuItem skuItem:dayGoodItem.sku_list){
                        int findSkuIndex = findSkuItemInList(skuItem.sku_id, weekGoodItem.sku_list);
                        if(findSkuIndex<0){
                            weekGoodItem.sku_list.add(skuItem);
                        }else {
                            weekGoodItem.sku_list.get(findSkuIndex).sale_count+=skuItem.sale_count;
                        }
                    }
                }
            }
        }

        Collections.sort(weekSalesResult.daySale, new GoodComparator());
        for (SaleResult.GoodItem goodItem : weekSalesResult.daySale) {
            Collections.sort(goodItem.sku_list, new SkuComparator());
        }

//        String result = JsonUtil.transferToJson(weekSalesResult);
        return weekSalesResult;
    }

//    private void writeDateToFile(String filename, String sales) {
//        FileWriter writer;
//        String fileName = saleDir + filename + ".txt";
//        try {
//            writer = new FileWriter(fileName);
//            writer.write("");
//            writer.write(sales);
//            writer.flush();
//            writer.close();
//            logger.info("写入到文件：" + fileName);
//
//        } catch (IOException e) {
//            logger.info("写入文件出错：" + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    @Override
    public SaleResult yesterday() {
//        Date today = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(today);
//        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
//        String date = new StringBuilder().append(calendar.get(Calendar.YEAR)).append("-")
//                .append(calendar.get(Calendar.MONTH) + 1).append("-").append(calendar.get(Calendar.DAY_OF_MONTH)).toString();
        String date = getDateBefore(1);
        SaleResult saleResult = getDateSales(date);
        return saleResult;
    }


    @Override
    public SaleResult getDateSales(String date) {
        SaleResult saleResult = new SaleResult(date, "日销量数据");

        PopClient client = new PopHttpClient(clientId, clientSecret);

        PddOrderListGetResponse firstResponse = getOrderListGetResponse(client, date, 1);
        if (firstResponse == null) {
            logger.info("获取数据失败");
            return null;
        }

        if (firstResponse.getOrderListGetResponse() == null) {
            if (firstResponse.getErrorResponse() != null) {
                logger.info(firstResponse.getErrorResponse().getErrorMsg());
            }
            logger.info("获取订单失败");
            return null;
        }

        List<PddOrderListGetResponse.OrderListGetResponseOrderListItem> firstOrderList = firstResponse.getOrderListGetResponse().getOrderList();
        int totalCount = firstResponse.getOrderListGetResponse().getTotalCount();
        //System.out.println("当日订单：" + totalCount + "条");
        if (firstOrderList == null) {
            logger.info("没有获取到订单数据");
        }
        // System.out.println("正在统计第一页订单:" + firstOrderList.size() + "条...");
        saleResult.total_order_count = totalCount;
        calOrderList(firstOrderList, saleResult);
        if (totalCount > firstOrderList.size()) {
            int page = totalCount % pageSize > 0 ? totalCount / pageSize + 1 : totalCount / pageSize;
            //System.out.println("剩余数据:" + (page - 1) + "页...");
            for (int i = 2; i <= page; i++) {
                PddOrderListGetResponse response = getOrderListGetResponse(client, date, i);
                List<PddOrderListGetResponse.OrderListGetResponseOrderListItem> orderList = response.getOrderListGetResponse().getOrderList();
                //System.out.println("正在统计第" + i + "页订单:" + orderList.size() + "条...");
                calOrderList(orderList, saleResult);
            }
        }

        Collections.sort(saleResult.daySale, new GoodComparator());
        for (SaleResult.GoodItem goodItem : saleResult.daySale) {
            Collections.sort(goodItem.sku_list, new SkuComparator());
        }
        logger.info(date + "销量统计完成");

        try {
            String jsonResult = JsonUtil.transferToJson(saleResult);
            TbDateSales tbSalesResult = new TbDateSales();
            tbSalesResult.setJsonSalesResult(jsonResult);
            tbSalesResult.setDate(date);
            save(tbSalesResult);
            logger.info("已保存数据库");
        } catch (Exception e) {
            logger.info("保存数据库出错" + e.getMessage());

        }
        return saleResult;

    }

    private PddOrderListGetResponse getOrderListGetResponse(PopClient client, String date, int page) {
        // System.out.println("正在获取第" + page + "页订单...");
        try {
            long startTime = dateToStamp(date + " " + daytimeStart) / 1000L;
            long endTime = dateToStamp(date + " " + daytimeEnd) / 1000L;
            return getOrderListGetResponse(client, 5, startTime, endTime, page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private PddOrderListGetResponse getOrderListGetResponse(PopClient client, int orderStatus, long startTime, long endTime, int page) {
        // System.out.println("正在获取第" + page + "页订单...");
        try {
            PddOrderListGetRequest request = new PddOrderListGetRequest();
            request.setOrderStatus(orderStatus);
            request.setRefundStatus(orderStatus == 1 ? 1 : 5);
            request.setStartConfirmAt(startTime);
            request.setEndConfirmAt(endTime);
            request.setPage(page);
            request.setPageSize(pageSize);
            PddOrderListGetResponse response = client.syncInvoke(request, getCurAccessToken());
            // System.out.println(JsonUtil.transferToJson(response));
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void calOrderList(List<PddOrderListGetResponse.OrderListGetResponseOrderListItem> orderList, SaleResult saleResult) {
        for (PddOrderListGetResponse.OrderListGetResponseOrderListItem orderListItem : orderList) {
            List<PddOrderListGetResponse.OrderListGetResponseOrderListItemItemListItem> itemList = orderListItem.getItemList();
            for (PddOrderListGetResponse.OrderListGetResponseOrderListItemItemListItem item : itemList) {
                int findGoodIndex = findGoodItemInList(item.getGoodsId(), saleResult.daySale);
                if (findGoodIndex == -1) {
                    SaleResult.GoodItem goodItem = new SaleResult.GoodItem();
                    goodItem.goods_id = item.getGoodsId();
                    goodItem.goods_name = item.getGoodsName();
                    goodItem.pay_amount += orderListItem.getPayAmount();
                    goodItem.sale_count += item.getGoodsCount();
                    SaleResult.SkuItem skuItem = new SaleResult.SkuItem();
                    skuItem.sku_id = item.getSkuId();
                    skuItem.spec = item.getGoodsSpec();
                    skuItem.img=item.getGoodsImg();
                    skuItem.sale_count += item.getGoodsCount();
                    goodItem.sku_list.add(skuItem);
                    saleResult.daySale.add(goodItem);
                } else {
                    SaleResult.GoodItem goodItem = saleResult.daySale.get(findGoodIndex);
                    goodItem.pay_amount += orderListItem.getPayAmount();
                    goodItem.sale_count += item.getGoodsCount();
                    int findSkuIndex = findSkuItemInList(item.getSkuId(), goodItem.sku_list);
                    if (findSkuIndex == -1) {
                        SaleResult.SkuItem skuItem = new SaleResult.SkuItem();
                        skuItem.sku_id = item.getSkuId();
                        skuItem.spec = item.getGoodsSpec();
                        skuItem.img=item.getGoodsImg();
                        skuItem.sale_count += item.getGoodsCount();
                        goodItem.sku_list.add(skuItem);
                    } else {
                        SaleResult.SkuItem skuItem = goodItem.sku_list.get(findSkuIndex);
                        skuItem.sale_count += item.getGoodsCount();
                    }
                }
                saleResult.pay_amount += orderListItem.getPayAmount();
            }

        }
    }

    public int findGoodItemInList(String goodId, List<SaleResult.GoodItem> goodItemList) {
        for (int i = 0; i < goodItemList.size(); i++) {
            SaleResult.GoodItem goodItem = goodItemList.get(i);
            if (goodId.equals(goodItem.goods_id)) {
                return i;
            }
        }
        return -1;
    }

    public int findSkuItemInList(String skuId, List<SaleResult.SkuItem> skuItemList) {
        for (int i = 0; i < skuItemList.size(); i++) {
            SaleResult.SkuItem skuItem = skuItemList.get(i);
            if (skuId.equals(skuItem.sku_id)) {
                return i;
            }
        }
        return -1;
    }

    public long dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        return date.getTime();
    }

    @Override
    public void save(TbDateSales tbDateSales) throws Exception {
        Date currentDate = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String date = simpleDateFormat.format(currentDate);
//        tbDateSales.setDate(date);
        tbDateSales.setCreattime(currentDate);
        tbDateSales.setUpdattime(currentDate);

        tbDateSalesMapper.insert(tbDateSales);
    }


}
