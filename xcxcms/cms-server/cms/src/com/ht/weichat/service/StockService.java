package com.ht.weichat.service;

import com.ht.weichat.pojo.TbDateSales;
import com.ht.weichat.pojo.TbStock;

/**
 * Created by  on 17/1/7.
 */
public interface StockService {


    void save(TbStock tbStock) throws Exception;
    StockResult getGoodInfo();
}
