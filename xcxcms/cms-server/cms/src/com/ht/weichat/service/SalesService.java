package com.ht.weichat.service;

import com.ht.weichat.pojo.TbDateSales;

/**
 * Created by  on 17/1/7.
 */
public interface SalesService {

    public String yesterday();
    public  String getCodeUrl();
    public String getAccessTokenFromCode(String code);
    public String getCurAccessToken();
    public String unsend();
    public String week();

    void save(TbDateSales tbDateSales) throws Exception;

}
