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
            <a href="${pageContext.request.contextPath}/query_week_sales">一周销量</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/refresh_access_token">刷新token</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/cms">一个参考示例cms</a>
        </td>
    </tr>
</table>

<<div id="token_value" style="text-align: center; margin-top: 20px;">
    access_token：${access_token}
</div>


</body>
</html>
