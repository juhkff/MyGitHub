<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="java.lang.reflect.Type" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="com.model.User" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2018/8/4
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>外包平台</title>
    <link rel="stylesheet" type="text/css" href="../css/afterLogin.css">
</head>
<body>
<%
    User user;
    String userType;
    user= (User) request.getAttribute("user");
    userType=user.getUserType();
    int length=0;
    if (userType!=null) {
        if (userType.equals("0")) {
            /*是学生*/
            length=5;
        } else {
            length=4;
        }
    }
%>
<header class="top_box">
    <h1 class="top_text">

    </h1>
</header>
<div class="content">
    <nav class="nav">
        <ul class="nav-ul">
            <%if (length==4){%>
            <li id="info-panel" onclick="changeBackGroundColor(this);changeFrame(this)">个人信息</li>
            <li id="publisher-label" onclick="changeBackGroundColor(this);changeFrame(this)">在发布的外包</li>
            <li id="finished-label" onclick="changeBackGroundColor(this);changeFrame(this)">已结束的外包</li>
            <li id="submit-label" onclick="changeBackGroundColor(this);changeFrame(this)">提交列表</li>
            <li id="new-label" onclick="changeBackGroundColor(this);changeFrame(this)">发布新外包</li>
            <li id="request-label" onclick="changeBackGroundColor(this);changeFrame(this)">外包申请</li>
            <%}else if (length==5){%>
            <li id="info-panel" onclick="changeBackGroundColor(this);changeFrame(this)">个人信息</li>
            <li id="received-label" onclick="changeBackGroundColor(this);changeFrame(this)">已接外包</li>
            <li id="completed-label" onclick="changeBackGroundColor(this);changeFrame(this)">已完成外包</li>
            <li id="refers-label" onclick="changeBackGroundColor(this);changeFrame(this)">偏好设置</li>
            <li id="showAll-label" onclick="changeBackGroundColor(this);changeFrame(this)">进入外包界面</li>
            <li id="teams-label" onclick="changeBackGroundColor(this);changeFrame(this)">团队界面</li>
            <%}%>
        </ul>
    </nav>
    <div class="right-container">
        <iframe class="container-frame" src="../login/info-panel" frameborder="0"></iframe>
    </div>
</div>
</body>
<script type="text/javascript" language="JavaScript" src="../js/afterLogin.js"></script>
</html>
