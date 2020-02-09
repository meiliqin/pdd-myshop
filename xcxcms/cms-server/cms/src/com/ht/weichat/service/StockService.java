package com.ht.weichat.service;

import com.ht.weichat.pojo.TbDateSales;
import com.ht.weichat.pojo.TbStock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by  on 17/1/7.
 */
public interface StockService {


    void insert(TbStock tbStock) throws Exception;
    List<TbStock>  queryStockList(List<String> skuIdList);
    StockResult getGoodInfo();
    void updateStockList(List<TbStock> tbStockList);
    void saveStock(List<TbStock> tbStockList);

    StockResult   queryStockResult ();

    StockResult syncSales(StockResult stockResult);



}
