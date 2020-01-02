
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http
        .PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddGoodsAddRequest;
import com.pdd.pop.sdk.http.api.request.PddGoodsListGetRequest;
import com.pdd.pop.sdk.http.api.request.PddOrderListGetRequest;
import com.pdd.pop.sdk.http.api.response.PddGoodsListGetResponse;
import com.pdd.pop.sdk.http.api.response.PddOrderListGetResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PopClientDemo {
    static final String clientId = ddMainP.clientId;
    static final String clientSecret = ddMainP.clientSecret;
    static final int pageSize = 100;
    static final String daytimeStart = "00:00:00";
    static final String daytimeEnd = "23:59:59";
    static final String date = "2019-12-30";
    static SaleResult saleResult = new SaleResult(date, "当日销量数据");


    public static void main(String[] args) throws Exception {
        String accessToken = "3e76ecaa088944d79f283d2872a2c60f51e099f7";

        PopClient client = new PopHttpClient(clientId, clientSecret);

        PddOrderListGetResponse firstResponse = getOrderListGetResponse(client, 1, accessToken);
        if (firstResponse == null) {
            System.out.println("获取数据失败");
            return;
        }
        List<PddOrderListGetResponse.OrderListGetResponseOrderListItem> firstOrderList = firstResponse.getOrderListGetResponse().getOrderList();
        int totalCount = firstResponse.getOrderListGetResponse().getTotalCount();
        System.out.println("当日订单：" + totalCount + "条");
        if (firstOrderList == null) {
            System.out.println("没有获取到订单数据");
            return;
        }
        System.out.println("正在统计第一页订单:" + firstOrderList.size() + "条...");
        calOrderList(firstOrderList);
        if (totalCount > firstOrderList.size()) {
            int page = totalCount % pageSize > 0 ? totalCount / pageSize + 1 : totalCount / pageSize;
            System.out.println("剩余数据:" + (page - 1) + "页...");
            for (int i = 2; i <= page; i++) {
                PddOrderListGetResponse response = getOrderListGetResponse(client, i, accessToken);
                List<PddOrderListGetResponse.OrderListGetResponseOrderListItem> orderList = response.getOrderListGetResponse().getOrderList();
                System.out.println("正在统计第"+i+"页订单:" + orderList.size() + "条...");
                calOrderList(orderList);
            }
        }

        Collections.sort(saleResult.daySale,new GoodComparator());
        for (GoodItem goodItem:saleResult.daySale){
            Collections.sort(goodItem.sku_list,new SkuComparator());
        }
        System.out.println("统计完成，输出结果：");
        System.out.println(JsonUtil.transferToJson(saleResult));


    }

    private static PddOrderListGetResponse getOrderListGetResponse(PopClient client, int page, String accessToken) {
        System.out.println("正在获取第" + page + "页订单...");
        try {
            PddOrderListGetRequest request = new PddOrderListGetRequest();
            request.setOrderStatus(5);
            request.setRefundStatus(5);
            request.setStartConfirmAt(dateToStamp(date + " " + daytimeStart) / 1000L);
            request.setEndConfirmAt(dateToStamp(date + " " + daytimeEnd) / 1000L);
            request.setPage(page);
            request.setPageSize(pageSize);
            PddOrderListGetResponse response = client.syncInvoke(request, accessToken);
            System.out.println(JsonUtil.transferToJson(response));
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void calOrderList(List<PddOrderListGetResponse.OrderListGetResponseOrderListItem> orderList) {
        for (PddOrderListGetResponse.OrderListGetResponseOrderListItem orderListItem : orderList) {
            List<PddOrderListGetResponse.OrderListGetResponseOrderListItemItemListItem> itemList = orderListItem.getItemList();
            for (PddOrderListGetResponse.OrderListGetResponseOrderListItemItemListItem item : itemList) {
                int findGoodIndex = findGoodItemInList(item.getGoodsId(), saleResult.daySale);
                if (findGoodIndex == -1) {
                    GoodItem goodItem = new GoodItem();
                    goodItem.goods_id = item.getGoodsId();
                    goodItem.goods_name = item.getGoodsName();
                    goodItem.pay_amount += orderListItem.getPayAmount();
                    goodItem.sale_count += item.getGoodsCount();
                    SkuItem skuItem = new SkuItem();
                    skuItem.sku_id = item.getSkuId();
                    skuItem.spec = item.getGoodsSpec();
                    skuItem.sale_count += item.getGoodsCount();
                    goodItem.sku_list.add(skuItem);
                    saleResult.daySale.add(goodItem);
                } else {
                    GoodItem goodItem = saleResult.daySale.get(findGoodIndex);
                    goodItem.pay_amount += orderListItem.getPayAmount();
                    goodItem.sale_count += item.getGoodsCount();
                    int findSkuIndex = findSkuItemInList(item.getSkuId(), goodItem.sku_list);
                    if (findSkuIndex == -1) {
                        SkuItem skuItem = new SkuItem();
                        skuItem.sku_id = item.getSkuId();
                        skuItem.spec = item.getGoodsSpec();
                        skuItem.sale_count += item.getGoodsCount();
                        goodItem.sku_list.add(skuItem);
                    } else {
                        SkuItem skuItem = goodItem.sku_list.get(findSkuIndex);
                        skuItem.sale_count += item.getGoodsCount();
                    }
                }
                saleResult.pay_amount += orderListItem.getPayAmount();
            }

        }
    }

    public static int findGoodItemInList(String goodId, List<GoodItem> goodItemList) {
        for (int i = 0; i < goodItemList.size(); i++) {
            GoodItem goodItem = goodItemList.get(i);
            if (goodId.equals(goodItem.goods_id)) {
                return i;
            }
        }
        return -1;
    }

    public static int findSkuItemInList(String skuId, List<SkuItem> skuItemList) {
        for (int i = 0; i < skuItemList.size(); i++) {
            SkuItem skuItem = skuItemList.get(i);
            if (skuId.equals(skuItem.sku_id)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        return date.getTime();
    }

    public static void main2(String[] args) throws Exception {
        String clientId = ddMainP.clientId;
        String clientSecret = ddMainP.clientSecret;
        String accessToken = "1e3df846fa574bb0a978bafe49fbb61fdd24d75e";
        PopClient client = new PopHttpClient(clientId, clientSecret);
        PddGoodsListGetRequest request = new PddGoodsListGetRequest();
//        request.setOuterId("str");
//        request.setIsOnsale(0);
//        request.setGoodsName("str");
//        request.setPageSize(0);
//        request.setPage(0);
//        request.setOuterGoodsId("str");
//        request.setCostTemplateId(0L);
        PddGoodsListGetResponse response = client.syncInvoke(request, accessToken);
        System.out.println(JsonUtil.transferToJson(response));
    }


}