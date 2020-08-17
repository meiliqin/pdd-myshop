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
    </tr>
</table>

<br>

<div class="login-box-body" align="center" >


        <form action="${pageContext.request.contextPath}/user/login" method="post">
            <input type="date" name="calendar" class="form-control" placeholder="选择日期查询销量">
            <button type="submit" class="btn btn-primary btn-block btn-flat">查询选定日期销量</button>
        </form>

</div>


<br>

<table cellpadding="0" cellspacing="0" border="1" width="500px" align="center">
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/refresh_access_token">刷新token</a>
        </td>
    </tr>
</table>
<div id="token_value" style="text-align: center; margin-top: 20px;">
    access_token：${access_token}
</div>
<br>
<table cellpadding="0" cellspacing="0" border="1" width="500px" align="center">
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/stock">库存管理</a>
        </td>
    </tr>
</table>
<br>





</body>
</html>
