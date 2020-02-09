package com.ht.weichat.mapper;

import com.ht.weichat.pojo.TbDateSales;
import com.ht.weichat.pojo.TbKeyValue;
import com.ht.weichat.pojo.TbStock;
import com.ht.weichat.service.StockLabel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TbStockMapper {

    int insert(TbStock record);

    TbStock selectByPrimaryKey(@Param("goodId")String goodId, @Param("skuId")String skuId);

    int updateByPrimaryKey(TbStock record);

    int updateStockQuantity(@Param("goodId")String goodId, @Param("skuId")String skuId, @Param("quantity")int quantity);

    int updateStockQuantityList(List<StockLabel> stockLabelList);

}