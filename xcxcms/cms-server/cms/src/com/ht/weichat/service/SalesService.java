package com.ht.weichat.service;

import com.ht.weichat.pojo.TbType;

import java.util.List;

/**
 * Created by  on 17/1/7.
 */
public interface SalesService {

    public String yesterday();
    public  String getCodeUrl();
    public String getAccessTokenFromCode(String code);
    public String unsend();
    public String week();


}
