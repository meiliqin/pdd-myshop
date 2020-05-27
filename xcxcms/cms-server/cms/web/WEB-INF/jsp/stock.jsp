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
    <script src="${pageContext.request.contextPath}/js/jquery-2.2.3.min.js"></script>

</head>
<body>


<%--<form method="post" action="${pageContext.request.contextPath}/save_stock">--%>

<table id="table" cellpadding="0" cellspacing="0" border="1" width="500px" align="center">
    <caption align="top">库存表</caption>
    <tr>
        <th>商品id</th>
<%--        <th>商品名</th>--%>
        <th>商品图片</th>
        <th>skuId</th>
        <th>sku描述</th>
        <th>库存数量</th>

    </tr>
    <tbody id="containner">
    <c:forEach var="goodItem" items="${stockResult.goodStockList}" varStatus="status">
        <c:forEach var="skuItem" items="${goodItem.sku_list}" varStatus="status">
            <tr>
                <td>${goodItem.goods_id}</td>
<%--                <td>${goodItem.goods_name}</td>--%>
                <td>
                    <img style="width: 100px; height: 100px;" src="${goodItem.thumb_url}">
                </td>
                <td>${skuItem.sku_id}</td>
                <td>${skuItem.spec}</td>

                    <%--            <td>${skuItem.sku_stock_quantity}</td>--%>
                <td>
                    <input type="number" name="goodItem.sku_list[${status.index}].stock_quantity"
                           value="${skuItem.sku_stock_quantity}">
                </td>

            </tr>
        </c:forEach>
    </c:forEach>

    </tbody>
</table>

<div id="sales_result" style="text-align: center; margin-top: 20px;">
    商品数：${stockResult.goodStockList.size()}
</div>
<%--    <input type="submit" value="提交" />--%>
<%--    <button type="submit" class="btn btn-primary btn-block btn-flat">提交</button>--%>
<div class="row">
    <%--        <input type="button" name="提交" value="提交" onclick="save_stock_quantity()"></input>--%>
    <%--        <input type="button" name="提交" value="提交" οnclick="save_stock_quantity()"></input>--%>
    <button onclick="save_stock_quantity()">提交</button>
</div>
<%--</form>--%>
</body>

<script type="text/javascript">

    function save_stock_quantity() {
        //  alert("save...");
        // var isDelete = window.confirm("确定删除"+title+"吗");
        var arry = [];
        var tr = $("#table tr");
        for (var i = 0; i < tr.length; i++) {
            var tds = $(tr[i]).find("td");
            if (tds.length > 0) {
                arry.push({
                    "goodId": $(tds[0]).html(),
                    "skuId": $(tds[2]).html(),
                    "stock_quantity": $(tds[4]).find("input").val(),
                });
            }
        }

        //var arrayData =
        $.ajax({
            url: "${pageContext.request.contextPath}/save_stock",
            data: {
                stockData: JSON.stringify(arry)
            },
            type: "post",
            success: function () {
                alert("success");
            }
        })
    }
</script>


</html>
