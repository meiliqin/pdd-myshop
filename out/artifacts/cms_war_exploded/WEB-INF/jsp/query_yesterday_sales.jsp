<%--
  Created by IntelliJ IDEA.
  User: meiliqin
  Date: 2020/1/28
  Time: 6:27 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONException" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>昨日销量统计</title>
</head>
<body>

<div id="sales_result" style="text-align: center; margin-top: 20px;">
    昨日销量：${yesterday_sales}
</div>
<div id="get_token" style="text-align: center; margin-top: 20px;">
    获取access_token地址(在新标签页中打开)：<a href="${codeUrl}">${codeUrl}</a>
</div>

<%--<div id="token2" style="text-align: center; margin-top: 20px;">--%>
<%--    请在浏览器打开该地址，将得到的code填入下方：${codeUrl}--%>
<%--</div>--%>
<form action="${pageContext.request.contextPath}/get_access_token" method="get">
    请输入回调地址https://mms.pinduoduo.com/home后的code值：<input type="text" name="code">
    <input type="submit" value="提交">
    <input type="reset" value="重置">
</form>

<div id="token_value" style="text-align: center; margin-top: 20px;">
    access_token：${access_token}
</div>
</body>
</html>
