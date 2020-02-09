package com.ht.weichat.controller.web;

import com.ht.weichat.pojo.TbAccount;
import com.ht.weichat.pojo.TbArticle;
import com.ht.weichat.pojo.TbStock;
import com.ht.weichat.pojo.TbType;
import com.ht.weichat.service.*;
import com.ht.weichat.utils.ConstantPool;
import com.pdd.pop.sdk.common.util.JsonUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @author cz
 */
@Controller
public class PageController {
    private Logger logger = Logger.getLogger("PageController");

    @Autowired
    private TypeService typeService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private StockService stockService;

    @RequestMapping("/")
    public String showIndex(Model model) {
        return "index";
    }

    @RequestMapping("/index")
    public String showIndex(Model model,
                            @RequestParam(defaultValue = "0") String token) {
        logger.info("token:" + token);
        String accessToken = salesService.getCurAccessToken();
        model.addAttribute("access_token", "已刷新,其值为" + accessToken);
        return "index";
    }

    @RequestMapping("/query_yesterday_sales")
    public String showYesterdaySales(Model model) {
        String result = salesService.yesterday();
        if (result.contains("access_token已过期")) {
            model.addAttribute("codeUrl", salesService.getCodeUrl());

        }
        model.addAttribute("yesterday_sales", result);

        return "query_yesterday_sales";
    }

    @RequestMapping("/refresh_access_token")
    public String refreshAccessToken(Model model) {
        String token = salesService.getCurAccessToken();
        String codeUrl = salesService.getCodeUrl();
        model.addAttribute("cur_access_token", token);
        model.addAttribute("codeUrl", codeUrl);
//        model.addAttribute("token_date", new Date());
        return "refresh_access_token";
    }

    @RequestMapping("/get_access_token")
    public String getAccessToken(Model model, HttpServletRequest request) {
        String code = request.getParameter("code");
        String token = salesService.getAccessTokenFromCode(code);
        return "redirect:/index?access_token=" + token;
    }

    @RequestMapping("/query_unsend_sales")
    public String showUnSendSales(Model model) {
        String result = salesService.unsend();
        model.addAttribute("unsend_sales", result);

        return "query_unsend_sales";
    }

    @RequestMapping("/query_week_sales")
    public String showWeekSales(Model model) {
        String result = salesService.week();
        model.addAttribute("week_sales", result);

        return "query_week_sales";
    }

    @RequestMapping("/stock")
    public String showStock(Model model) {
        logger.info("正在执行showStock");
        StockResult stockResult = stockService.getGoodInfo();
        //todo 这个表中可去掉除 goodid skuid stock之外的无关字段
        //todo 应改为批量查询 会快很多

        if (stockResult != null) {
            List<StockResult.GoodItem> goodStockList=stockResult.goodStockList;
            try {
                for(StockResult.GoodItem goodItem:goodStockList){
                    for (StockResult.SkuItem skuItem:goodItem.sku_list){
                        TbStock tbStock= stockService.queryStock(String.valueOf(goodItem.goods_id),String.valueOf(skuItem.sku_id));
                        if(tbStock==null){
                            tbStock=new TbStock();
                            tbStock.setGoodId(String.valueOf(goodItem.goods_id));
                            tbStock.setSkuId(String.valueOf(skuItem.sku_id));
                            tbStock.setStock(0);
                            stockService.insert(tbStock);
                        }else {
                            logger.info("查询到库存："+tbStock.getStock());
                        }
                        skuItem.setSku_stock_quantity(tbStock.getStock());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

//            model.addAttribute("goodStockList", goodStockList);
//            model.addAttribute("goodSize", goodStockList.size());
            model.addAttribute("stockResult", stockResult);
        }
        return "stock";
    }

    // @RequestMapping(value = "save_stock",method = RequestMethod.POST)
//    public String saveStock(
//            @ModelAttribute StockResult stockResult,
//            Model model,
//            HttpServletRequest request){
    @RequestMapping(value = "save_stock")
    public String saveStock(
            Model model,
            HttpServletRequest request
    ) {
        logger.info("正在执行saveStock");
        String jsonData = request.getParameter("stockData");
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            List<StockLabel>stockLabelList=new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                StockLabel stockLabel=new StockLabel();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                stockLabel.setGoodId(jsonObject.getString("goodId"));
                stockLabel.setSkuId(jsonObject.getString("skuId"));
                stockLabel.setStock(jsonObject.getInt("stock_quantity"));
                stockLabelList.add(stockLabel);
                logger.info("skuId:"+stockLabel.getSkuId()+"quantity:"+stockLabel.getStock());
//                stockService.update(goodId,skuId,quantity);
            }
            stockService.updateStockList(stockLabelList);
            logger.info("已经保存数据");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "stock";

    }

    @RequestMapping("/cms")
    public String showCms(Model model) {
        return "login";
    }

    @RequestMapping("/admin")
    public String showAdmin(Model model, HttpServletRequest request) {
        TbAccount account = (TbAccount) request.getSession().getAttribute("global.account");
//        if (account == null){
        return "login";
//        } else {
//            return "redirect:/home";
//        }
    }


    @RequestMapping("/home")
    public String home(
            Model model,
            @RequestParam(defaultValue = "0") int id
    ) {

        List<TbType> titleList = typeService.list();

        if (titleList.size() == 0) {
            return "redirect:/type/home";
        }

        model.addAttribute("titleList", titleList);

        TbType defaultTitle = null;
        if (id == 0) {
            defaultTitle = titleList.get(0);
            model.addAttribute("topTitle", defaultTitle);
        } else {
            defaultTitle = typeService.findById(id);
            model.addAttribute("topTitle", defaultTitle);
        }

        List<TbArticle> countList = articleService.listById(defaultTitle.getId());
        int pageSize = countList.size() / ConstantPool.PAGESIZE;
        model.addAttribute("totalCount", countList.size() % ConstantPool.PAGESIZE == 0 ? pageSize : pageSize + 1);

        List<TbArticle> itemList = articleService.listById(defaultTitle.getId(), 1);

        for (int i = 0; i < itemList.size(); i++) {
            TbArticle tbArticle = itemList.get(i);
            if (tbArticle.getContent() != null && tbArticle.getContent().length() > 50) {
                tbArticle.setContent(tbArticle.getContent().substring(0, 50));
            }
        }

        model.addAttribute("itemList", itemList);
        return "home";
    }

}
