package com.ht.weichat.service;

import com.pdd.pop.ext.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * created by n3207
 * on 2020/1/3
 */
public class StockResult {
    @JsonProperty("good_stock_list")
    public List<GoodItem> goodStockList;


    public StockResult() {
        goodStockList = new ArrayList<>();

    }

    public static class GoodItem {
        @JsonProperty("thumb_url")
        public String thumb_url;
        @JsonProperty("goods_name")
        public String goods_name;
        @JsonProperty("goods_id")
        public long goods_id;
        @JsonProperty("good_stock_quantity")
        public int good_stock_quantity;
        @JsonProperty("sku_list")
        public List<SkuItem> sku_list;

        public GoodItem() {
            sku_list = new ArrayList<>();
        }

        public String getThumb_url() {
            return thumb_url;
        }

        public void setThumb_url(String thumb_url) {
            this.thumb_url = thumb_url;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public long getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(long goods_id) {
            this.goods_id = goods_id;
        }

        public int getGood_stock_quantity() {
            return good_stock_quantity;
        }

        public void setGood_stock_quantity(int good_stock_quantity) {
            this.good_stock_quantity = good_stock_quantity;
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
        public long sku_id;
        @JsonProperty("sku_stock_quantity")
        public int sku_stock_quantity;

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public long getSku_id() {
            return sku_id;
        }

        public void setSku_id(long sku_id) {
            this.sku_id = sku_id;
        }

        public int getSku_stock_quantity() {
            return sku_stock_quantity;
        }

        public void setSku_stock_quantity(int sku_stock_quantity) {
            this.sku_stock_quantity = sku_stock_quantity;
        }
    }
}
