package com.ht.weichat.pojo;

import java.util.Date;

public class TbStock {
    private String goodId;

    private String skuId;

    private String goodName;

    private String thumbUrl;

    private String skuSpec;

    private String skuUrl;

    private int stock;

    private Date updatTime;


    private Date creatTime;

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getSkuSpec() {
        return skuSpec;
    }

    public void setSkuSpec(String skuSpec) {
        this.skuSpec = skuSpec;
    }

    public String getSkuUrl() {
        return skuUrl;
    }

    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getUpdatTime() {
        return updatTime;
    }

    public void setUpdatTime(Date updattime) {
        this.updatTime = updattime;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creattime) {
        this.creatTime = creattime;
    }



}
