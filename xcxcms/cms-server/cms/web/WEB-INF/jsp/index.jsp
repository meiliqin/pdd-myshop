<%--
  Created by IntelliJ IDEA.
  User: meiliqin
  Date: 2020/1/28
  Time: 6:20 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询销量</title>
</head>
<body>

<table cellpadding="0" cellspacing="0" border="1" width="500px" align="center">
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/query_unsend_sales">待发货销量</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/query_yesterday_sales">昨日销量</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/user/logout">退出</a>
        </td>
    </tr>
</table>
<%--<form action="${pageContext.request.contextPath}/query_sales" method="get">--%>
<%--    请输入城市名：<input type="text" name="city_name">--%>
<%--    <input type="submit" value="提交">--%>
<%--    <input type="reset" value="重置">--%>
<%--</form>--%>
</body>
</html>
