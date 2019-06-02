<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/16/2018
  Time: 12:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>创建新团队</title>
    <style type="text/css" rel="stylesheet">
        h1{
            margin: 10px;
            min-width: 250px;
        }

        .name-label{
            font-size: 30px;
            width: 95%;
            min-width: 300px;
            padding: 0;
            margin: 10px;
            padding-right: 10px;
        }

        .team-i {
            display: block;
            margin: 10px auto;
            width: 250px;
            height: 50px;
            font-size: 30px;
            text-align: center;
            border-radius: 25px;
            background-color: #169ad4;
            color: white;
            cursor: pointer;
        }

        .team-i:hover{
            background-color: #66cc66;
            border: none;
            border: 0;
        }

        .content-label {
            font-size: 30px;
            width: 95%;
            min-width: 300px;
            padding: 0;
            margin: 10px;
            padding-right: 10px;
            height: 350px;
        }

    </style>
</head>
<body>
<%--<form id="team" action="createTeam">--%>
<h1>团队名:</h1>
    <input class="name-label" type="text" placeholder="输入您的团队名称" name="teamName">
    <input style="display: none" type="text" name="userName" value="${cookie.web_userName.value}">
    <input style="display: none" type="text" name="stuId" value="${cookie.web_status2.value}">
    <input style="display: none" type="text" name="realName" value="${cookie.web_realName.value}">
    <h1>团队介绍:</h1>
    <textarea class="content-label" <%--form="team" --%>name="teamIntro" placeholder="输入你的团队介绍"></textarea>
    <input class="team-i" type="submit" value="创建团队" onclick="{
      document.cookie='web_isLeader=1;path=/webLab';
      document.cookie='teamName='+document.getElementsByClassName('name-label')[0].value+';path=/webLab';
      location.href=location.href.split('createNewTeam')[0]+'createTeam?userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}&teamName='+document.getElementsByClassName('name-label')[0].value+
            '&stuId=${cookie.web_status2.value}&realName=${cookie.web_realName.value}&teamIntro='+document.getElementsByClassName('content-label')[0].innerHTML;
    }">
<%--</form>--%>
</body>
</html>
