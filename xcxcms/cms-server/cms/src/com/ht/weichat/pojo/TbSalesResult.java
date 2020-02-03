package com.ht.weichat.pojo;

import java.util.Date;

public class TbSalesResult {
    private Integer id;

    private Date date;
    private String jsonSalesResult;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJsonSalesResult() {
        return jsonSalesResult;
    }

    public void setJsonSalesResult(String jsonSalesResult) {
        this.jsonSalesResult = jsonSalesResult;
    }
}
