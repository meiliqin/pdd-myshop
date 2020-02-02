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
}
