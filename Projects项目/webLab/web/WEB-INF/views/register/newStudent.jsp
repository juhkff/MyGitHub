<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.springframework.ui.Model" %>

<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2018/8/4
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>新用户创建</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/newGuest.css">
    <script src="${pageContext.request.contextPath}/js/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/js/newStudent.js"></script>
</head>
<body>
<div class="container">
    <h1 class="header">新客户注册</h1>
    <table>
        <tr>
            <td class="td_left">用户名：</td><td class="td_right"><input type="text" placeholder="用户名"></td>
        </tr>
        <tr>
            <td class="td_left">密码：</td><td class="td_right"><input type="password" placeholder="密码"></td>
        </tr>
        <tr>
            <td class="td_left">手机号码：</td><td class="td_right a"><input type="number" placeholder="手机号码"></td>
        </tr>
        <tr>
            <td class="td_left">（选填）Email：</td><td class="td_right"><input type="email" placeholder="电子邮箱地址"></td>
        </tr>
    </table>
    <div class="separator"></div>
    <h1 class="header">实名信息</h1>
    <table>
        <tr>
            <td class="td_left">学校：</td><td class="td_right"><input type="text" placeholder="学校"></td>
        </tr>
        <tr>
            <td class="td_left">学号：</td><td class="td_right"><input type="number" placeholder="学号"></td>
        </tr>
        <tr>
            <td class="td_left">真实姓名：</td><td class="td_right"><input type="text" placeholder="真实姓名"></td>
        </tr>
        <tr>
            <td class="td_left">身份证号码：</td><td class="td_right"><input type="text" placeholder="身份证号码"></td>
        </tr>
        <tr>
            <td class="td_left">性别：</td><td class="td_right"><input class="i_radio" type="radio" name="sex" value="M">男<input class="i_radio" type="radio" name="sex" value="F">女</td>
        </tr>
    </table>
    <div class="separator"></div>
    <input class="i_submit" type="submit" value="加入我们！" onclick="register()">
</div>
</body>
</html>
