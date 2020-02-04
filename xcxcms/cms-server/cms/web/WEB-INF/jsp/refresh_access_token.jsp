<%--
  Created by IntelliJ IDEA.
  User: meiliqin
  Date: 2020/1/28
  Time: 6:27 下午
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>刷新token</title>
</head>
<body>

<div id="cur_token" style="text-align: center; margin-top: 20px;">
    当前access_token：${cur_access_token}
</div>
<div id="get_code" style="text-align: center; margin-top: 20px;">
    获取access_token地址：<a href="${codeUrl}" target="_blank">${codeUrl}</a>
</div>

<div id="get_token" style="text-align: center; margin-top: 20px;">
<form action="${pageContext.request.contextPath}/get_access_token" method="get">
    请输入回调地址https://mms.pinduoduo.com/home后的code值：<input type="text" name="code">
    <input type="submit" value="提交">
    <input type="reset" value="重置">
</form>
</div>
</body>
</html>
