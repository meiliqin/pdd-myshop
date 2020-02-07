package com.ht.weichat.mapper;

import com.ht.weichat.pojo.TbArticle;
import com.ht.weichat.pojo.TbDateSales;
import com.ht.weichat.pojo.TbKeyValue;
import com.ht.weichat.pojo.TbStock;

public interface TbKeyValueMapper {

    int insert(TbKeyValue keyValue);

    TbKeyValue selectByPrimaryKey(String key);

    int updateByPrimaryKey(TbKeyValue record);



}