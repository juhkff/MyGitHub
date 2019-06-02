<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2018/8/2
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>新用户注册</title>
    <link rel="stylesheet" type="text/css" href="../css/register.css">
    <script type="text/javascript" src="../js/register.js"></script>
</head>
<body>
<div class="container">
    <div class="ChooseType">
        <div class="top_box">
            请选择你的身份
        </div>
        <div class="bottom_box">
            <div class="bottom_left" onclick="getType(this)">客户</div>
            <div class="bottom_right" onclick="getType(this)">学生</div>
        </div>
    </div>
</div>
</body>
</html>
