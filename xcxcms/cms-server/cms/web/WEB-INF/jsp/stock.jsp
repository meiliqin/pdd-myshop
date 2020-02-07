<%--
  Created by IntelliJ IDEA.
  User: meiliqin
  Date: 2020/1/28
  Time: 6:27 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>库存管理</title>
</head>
<body>



<table cellpadding="0" cellspacing="0" border="1" width="500px" align="center">
    <caption align="top">库存表</caption>
    <tr>
        <th>商品id</th>
        <th>商品名</th>
        <th>商品图片</th>
        <th>skuId</th>
        <th>sku描述</th>
        <th>库存数量</th>
    </tr>
    <tbody id="containner">
    <c:forEach var="itemBean" items="${goodStockList}" varStatus="status">
        <c:forEach var="skuItem" items="${itemBean.sku_list}" varStatus="status">
        <tr>
            <td>${itemBean.goods_id}</td>
            <td>${itemBean.goods_name}</td>
            <td>
                <img style="width: 100px; height: 100px;" src="${itemBean.thumb_url}">
            </td>
            <td>${skuItem.sku_id}</td>
            <td>${skuItem.spec}</td>

            <td>${skuItem.sku_stock_quantity}</td>
        </tr>
        </c:forEach>
    </c:forEach>

    </tbody>
</table>

<div id="sales_result" style="text-align: center; margin-top: 20px;">
    商品数：${goodSize}
</div>
</body>
</html>
