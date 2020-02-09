package com.ht.weichat.service;

import com.ht.weichat.pojo.TbDateSales;
import com.ht.weichat.pojo.TbStock;

import java.util.Date;
import java.util.List;

/**
 * Created by  on 17/1/7.
 */
public interface StockService {


    void insert(TbStock tbStock) throws Exception;
    TbStock queryStock(String goodId, String skuId);
    List<TbStock>  queryStockList(List<String> skuIdList);

    StockResult getGoodInfo();
    void update(String goodId, String skuId, int quantity);
    void updateStockList(List<TbStock> tbStockList);

    StockResult   queryStockResult ();

    StockResult syncSales(StockResult stockResult);



}
