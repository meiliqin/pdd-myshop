<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<div id="sales_result1" style="text-align: center; margin-top: 20px;">
    ${yesterday_sales}
</div>
<br>
<div id="sales_result2" style="text-align: center; margin-top: 20px;">
    日期：${saleResult.date}<br>
    订单总数：${saleResult.total_order_count}<br>
    销售额：${saleResult.pay_amount}
</div>
<br>
<table id="table" cellpadding="0" cellspacing="0" border="1" width="500px" align="center">
    <caption align="top">商品销量表</caption>
    <tr>
        <th>商品图片</th>
        <th>商品名</th>
        <th>sku描述</th>
        <th>销售数量</th>
    </tr>
    <tbody id="containner">
    <c:forEach var="goodItem" items="${saleResult.daySale}" varStatus="status">
        <c:forEach var="skuItem" items="${goodItem.sku_list}" varStatus="status">
            <tr>
                <td>
                    <img style="width: 100px; height: 100px;" src="${skuItem.img}">
                </td>
                <td>${goodItem.goods_name}</td>
                <td>${skuItem.spec}</td>
                <td>${skuItem.sale_count}</td>

            </tr>
        </c:forEach>
    </c:forEach>

    </tbody>
</table>

</body>
</html>
