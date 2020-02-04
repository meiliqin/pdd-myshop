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
</body>
</html>
