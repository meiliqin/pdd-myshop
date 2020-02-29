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

    public List<GoodItem> getDaySale() {
        return daySale;
    }

    public void setDaySale(List<GoodItem> daySale) {
        this.daySale = daySale;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(Double pay_amount) {
        this.pay_amount = pay_amount;
    }

    public int getTotal_order_count() {
        return total_order_count;
    }

    public void setTotal_order_count(int total_order_count) {
        this.total_order_count = total_order_count;
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

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public int getSale_count() {
            return sale_count;
        }

        public void setSale_count(int sale_count) {
            this.sale_count = sale_count;
        }

        public Double getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(Double pay_amount) {
            this.pay_amount = pay_amount;
        }

        public List<SkuItem> getSku_list() {
            return sku_list;
        }

        public void setSku_list(List<SkuItem> sku_list) {
            this.sku_list = sku_list;
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

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getSku_id() {
            return sku_id;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public int getSale_count() {
            return sale_count;
        }

        public void setSale_count(int sale_count) {
            this.sale_count = sale_count;
        }
    }
}
