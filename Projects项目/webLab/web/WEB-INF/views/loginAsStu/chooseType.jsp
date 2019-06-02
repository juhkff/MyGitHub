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
    <title>选择外包类型</title>
    <link rel="stylesheet" type="text/css" href="../css/register.css">
    <script type="text/javascript" src="../js/register.js"></script>
</head>
<body>
<div class="container">
    <div class="ChooseType">
        <div class="top_box">
            请选择外包类型
        </div>
        <div class="bottom_box">
            <div class="bottom_left" onclick="getType2(this)">个人</div>
            <div class="bottom_right" onclick="getType2(this)">团队</div>
        </div>
    </div>
</div>
</body>
<%

%>
<script type="text/javascript" language="JavaScript">
    function getType2(evt) {
        /*location.href=location.href.split("pros")[0];*/
        if(evt.className==="bottom_left"){
            location.href=location.href.split("pros")[0]+"login/sendRequest?userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}&proName=<%=request.getAttribute("proName")%>&receiverName=${cookie.web_userName.value}&proType=1";
        }else if(evt.className==="bottom_right"){
            location.href=location.href.split("pros")[0]+"login/sendRequest?userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}&proName=<%=request.getAttribute("proName")%>&receiverName=${cookie.web_teamName.value}&proType=2";
        }
    }
</script>
</html>
