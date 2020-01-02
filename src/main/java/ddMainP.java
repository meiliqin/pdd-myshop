import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ddMainP {
    public static String clientId = "864a62d925204fa29d5a2d2b22d8c67e";
    public static String clientSecret = "f821108d75c0dc7951e94cdd27103a28d19c7839";
    public static String redirectUri = "https://mms.pinduoduo.com";
    public static String accessToken="";

    public static void main(String arg[]) {
        System.out.println("12345");
        PopAccessTokenClient client = new PopAccessTokenClient(
                clientId,
                clientSecret);
        try {
            String CodeUrl = getCodeUrl();
            System.out.println("CodeUrl:" + CodeUrl);
            String code = "e93172c158c7497da3f0511cad6f7410fb801bc3";
            AccessTokenResponse accessTokenResponse = client.generate(code);
            accessToken = accessTokenResponse.getAccessToken();
//            String ownerName = accessTokenResponse.getOwnerName();
//            String ownerId = accessTokenResponse.getOwnerId();

            System.out.println("accessToken:" + accessToken);
//            System.out.println("ownerName:" + ownerName);
//            System.out.println("ownerId:" + ownerId);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 拼多多 授权码 ---授权地址
     * 获取授权码（code）
     * 商家授权正式环境：http://mms.pinduoduo.com/open.html
     * 多多客授权正式环境：http://jinbao.pinduoduo.com/open.html
     * 参考    http://open.pinduoduo.com/#/document
     * 返回地址:
     * http://ddjb.pinduoduo.com/open.html?client_id=745f2d713c4140cea73e61a316af82ab&response_type=code&redirect_uri=http%3A%2F%2Fpinfa.chcvn.com%2Fpdd_ddk%2Fpddcode
     */
    public static String getCodeUrl() {
        String url = "http://mms.pinduoduo.com/open.html";
        //client_id
        url += "?client_id=" + clientId;
        //授权类型为CODE
        url += "&response_type=code";
        //授权回调地址
        url += "&redirect_uri=" + redirectUri;
        return url;
    }


}
