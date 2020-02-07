package com.ht.weichat.service;

import com.pdd.pop.ext.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * created by n3207
 * on 2020/1/2
 */
public class SaleResult {
    @JsonProperty("daySale")
    public List<GoodItem>daySale;
    @JsonProperty("date")
    public String date;
    @JsonProperty("description")
    public String description;
    @JsonProperty("pay_amount")
    public Double pay_amount;
    @JsonProperty("total_order_count")
    public int total_order_count;

    public SaleResult(String date, String description) {
        this.date = date;
        this.description = description;
        daySale=new ArrayList<>();
        pay_amount=0.0;
        total_order_count=0;
    }

    public SaleResult() {
    }

    public static class GoodItem {
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

    public static class SkuItem {
        @JsonProperty("spec")
        public String spec;
        @JsonProperty("sku_id")
        public String sku_id;
        @JsonProperty("sale_count")
        public int sale_count;

        public SkuItem() {
            sale_count=0;
        }
    }
}
