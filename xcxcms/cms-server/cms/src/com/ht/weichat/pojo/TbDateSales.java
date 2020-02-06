package com.ht.weichat.pojo;

import java.util.Date;

public class TbDateSales {
    private String date;

    private Date updattime;

    private Date creattime;

    private String salesResult;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getUpdattime() {
        return updattime;
    }

    public void setUpdattime(Date updattime) {
        this.updattime = updattime;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public String getJsonSalesResult() {
        return salesResult;
    }

    public void setJsonSalesResult(String jsonSalesResult) {
        this.salesResult = jsonSalesResult;
    }


}
