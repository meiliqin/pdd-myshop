package com.ht.weichat.mapper;

import com.ht.weichat.pojo.TbStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbStockMapper {

    int insert(TbStock record);

    TbStock selectByPrimaryKey(@Param("goodId")String goodId, @Param("skuId")String skuId);

    List<TbStock> selectStocksByPrimaryKey(List<String> skuIdList);


    int updateByPrimaryKey(TbStock record);

    int updateStockQuantity(@Param("goodId")String goodId, @Param("skuId")String skuId, @Param("quantity")int quantity);

    int updateStockQuantityList(List<TbStock> tbStockList);

}