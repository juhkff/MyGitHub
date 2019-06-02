<%@ page import="com.model.Project" %><%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/14/2018
  Time: 4:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>外包详情</title>
    <style type="text/css" rel="stylesheet">
        body{
            margin: 0;
            padding: 0;
            background-color: grey;
        }

        h1{
            margin: 0;
            padding: 0;
        }

        form{
            margin-bottom: 0;
        }

        input{
            padding: 0;
            border: none;
        }

        td{
            padding: 0;
        }

        .i-back ,.i-cancel{
            border-radius: 10px;
            width: 210px;
            height: 60px;;
            border: none;
            background-color: #169ad4;
            color: white;
            font-size: 30px;
            cursor: pointer;
        }
        .i-back:hover ,.i-cancel:hover{
            background-color: #77df77;
            color: #169ad4;
        }

        .blank-div{
            height: 85px;
            background-color: #66cc66;
        }

        .title-top{
            padding: 10px;
            border-bottom: black solid 1px;
        }

        /*.title-top > h1{*/
        /*margin-top: 5px;*/
        /*margin-left: 5px;*/
        /*margin-bottom: 5px;*/
        /*}*/

        .column-header{
            padding: 10px;
            border-bottom: dashed 2px black;
        }

        .back{
            float: left;
        }

        /*.i-back{*/
        /*width: 210px;*/
        /*height: 60px;*/
        /*}*/

        /*.cancel*/
        .request{
            float: right;
        }

        /*.i-cancel{*/
        /*width: 210px;*/
        /*height: 60px;*/
        /*}*/

        .title {
            height: 70px;
            text-align: center;
        }

        .title > h1{
            margin: auto;
            padding-top: 5px;
            padding-bottom: 5px;
            border: none;
            border-bottom: dashed 1px black;
            height: 60px;
            font-size: 50px;
        }

        .refers-div{
            display: flex;
            flex-direction: column;
            /*padding-bottom: 10px;*/
            padding: 10px;
            border-bottom: dashed 2px black;
        }

        .refers-div > h1{
            /*margin-left: 20px;*/
            /*margin-top: 5px;*/
            margin-bottom: 5px;
        }

        .refers-div > input{
            /*margin-left: 20px;*/
            /*margin-right: 20px;*/
            flex-grow: 1;
            font-size: 25px;
        }

        .content-div{
            display: flex;
            flex-direction: column;
            /*padding-bottom: 10px;*/
            padding: 10px;
            /*border-bottom: dashed 2px black;*/
        }

        .content-div > h1{
            /*margin-left: 20px;
            margin-top: 5px;*/
            margin-bottom: 5px;
        }

        .content-div > textarea{
            /*margin-left: 20px;
            margin-right: 20px;*/
            flex-grow: 1;
            font-size: 25px;
        }

        .table-div{
            padding: 10px;
        }

        table{
            width: 100%;
        }

        .label{
            height: 35px;
            width: 210px;
            font-size: 28px;
            text-align: right;
        }

        td{
            display: flex;
            justify-content: right;
            align-items: center;
        }

        td > input{
            height: 35px;
            font-size: 28px;
        }

        .left{
            float: left;
        }

        .right{
            float: right;
        }

        /*.special{
            margin-right: 50px;
        }*/

        .label.right.special{
            position: relative;
            right: -100px;
            margin-right: 100px;
        }
    </style>
    <%
        Project project= (Project) request.getAttribute("proInfo");
    %>
</head>
<body>
<%--<div class="blank-div"></div>--%>
<div class="title-top"><h1>详细信息</h1></div>
<div class="column-header">
    <form class="back" action="../login/showAll-label<%--?userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}--%>" method="get">
        <input readonly="readonly" style="display: none" type="text" name="userId" value="${cookie.web_userId.value}">
        <input readonly="readonly" style="display: none" type="text" name="userName" value="${cookie.web_userName.value}">
        <input class="i-back" type="submit" value="返回列表">
    </form>
    <form class="request" action="../login/sendRequest<%--?proName=<%=project.getName()%>--%>" method="get">
        <input style="display: none" type="text" name="userId" value="${cookie.web_userId.value}">
        <input style="display: none" type="text" name="userName" value="${cookie.web_userName.value}">
        <input style="display: none" type="text" name="proName" value="<%=project.getName()%>">
        <input style="display: none" type="text" name="receiverName" value="${cookie.web_userName.value}">
        <input style="display: none" type="text" name="proType" value="1">                          <%--1:个人; 2:团队--%>
        <input class="i-cancel" type="submit" value="接受外包" onclick="verification(this)">
    </form>
    <div style="clear: both"></div>
</div>
<div class="title">
    <h1><%=project.getName()%></h1>
</div>
<div class="refers-div">
    <h1>标签：</h1>
    <input readonly="readonly" type="text" name="pro_refers" placeholder="输入标签（多个标签之间用空格分隔）" value="<%=project.getTags()%>">
</div>
<div class="content-div">
    <h1>内容介绍：</h1>
    <textarea readonly="readonly" name="pro_content" placeholder="外包内容/外包任务" <%--form="pros-form"--%> rows="7"><%=project.getContent()%></textarea>
</div>
<div class="table-div">
    <table>
        <tr>
            <td class="label left">报酬：</td><td class="left"><input readonly="readonly" type="text" placeholder="暂无" value="<%=project.getReward()%>"></td><td class="right special"><input readonly="readonly" type="text" placeholder="暂无" value="<%=project.getFounder()%>"></td><td class="label right special">发布者：</td>
        </tr>
        <tr>
            <td class="label left">规定完成时间：</td><td class="left"><input readonly="readonly" type="text" placeholder="暂无" value="<%=project.getSettedTime()%>"></td><td class="right special"><input readonly="readonly" type="text" placeholder="暂无" value="<%=project.getPhoneNum()%>"></td><td class="label right special">手机号：</td>
        </tr>
    </table>
</div>
</body>
<script type="text/javascript" language="JavaScript">
    var receiverName=document.createElement("input");
    var form=document.getElementsByClassName("request")[0];
    function verification(target) {
        /*receiverName.style.display="none";
        receiverName.type="text";
        receiverName.name="receiverName";
        receiverName.value=${cookie.web_userName.value};*/
        if ((${cookie.web_isLeader.value})=="0"){
            //个人
            /*form.appendChild(receiverName);*/
        }else{
            //可以团队
            form.action="chooseType";
        }
    }
</script>
</html>
