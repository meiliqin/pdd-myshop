package com.ht.weichat.service;

import com.pdd.pop.ext.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GoodItem {
//    @JsonProperty("thumb_url")
//    String thumb_url;
    @JsonProperty("goods_name")
   public String goods_name;
    @JsonProperty("goods_id")
    public String goods_id;
    @JsonProperty("sale_count")
    public int sale_count;
    @JsonProperty("pay_amount")
    public Double pay_amount;
    @JsonProperty("sku_list")
   public List<SkuItem> sku_list ;

    public GoodItem() {
        sku_list= new ArrayList<>();
        pay_amount=0.0;
        sale_count=0;
    }
}
