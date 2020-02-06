package com.ht.weichat.mapper;

import com.ht.weichat.pojo.TbArticle;
import com.ht.weichat.pojo.TbDateSales;

public interface TbDateSalesMapper {

    int insert(TbDateSales record);

    int insertSelective(TbDateSales record);

    TbDateSales selectByPrimaryKey(String date);



}