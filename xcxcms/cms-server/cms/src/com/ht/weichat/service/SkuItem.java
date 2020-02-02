package com.ht.weichat.service;

import com.pdd.pop.ext.fasterxml.jackson.annotation.JsonProperty;

public class SkuItem {
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
