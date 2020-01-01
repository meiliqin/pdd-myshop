
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http
        .PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddGoodsListGetRequest;
import com.pdd.pop.sdk.http.api.request.PddOrderListGetRequest;
import com.pdd.pop.sdk.http.api.response.PddGoodsListGetResponse;
import com.pdd.pop.sdk.http.api.response.PddOrderListGetResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PopClientDemo {

    public static void main(String[] args) throws Exception {

        String clientId = pddMain.clientId;
        String clientSecret = pddMain.clientSecret;
        String accessToken = "1e3df846fa574bb0a978bafe49fbb61fdd24d75e";

        String timeStart="2019-12-31 00:00:00";
        String timeEnd="2019-12-31 23:59:59";

        PopClient client = new PopHttpClient(clientId, clientSecret);
        PddOrderListGetRequest request = new PddOrderListGetRequest();
        request.setOrderStatus(5);
        request.setRefundStatus(5);
        System.out.println(dateToStamp(timeStart));
        System.out.println(dateToStamp(timeEnd));
        request.setStartConfirmAt(dateToStamp(timeStart)/1000L);
        request.setEndConfirmAt(dateToStamp(timeEnd)/1000L);
        request.setPage(1);
        request.setPageSize(100);
//        request.setTradeType(0);
//        request.setUseHasNext(false);
        PddOrderListGetResponse response = client.syncInvoke(request, accessToken);
        //response.getOrderListGetResponse().getOrderList()
        System.out.println(JsonUtil.transferToJson(response));


    }


    public static void main2(String[] args) throws Exception {

        String clientId = pddMain.clientId;
        String clientSecret = pddMain.clientSecret;
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
    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        return date.getTime();
    }

}