<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Team" %><%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/15/2018
  Time: 5:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>团队列表</title>
    <style type="text/css" rel="stylesheet">
        h1,h2{
            margin: 0;
            padding: 0;
        }

        h1{
            font-size: 50px;
            font-weight: bold;
            text-align: center;
            border-bottom: solid 1px black;
        }

        h2{
            font-size: 40px;
            font-weight: bold;
            text-align: center;
            padding-top: 10px;
            padding-bottom: 10px;
            border-bottom: solid 1px black;
        }

        table{
            width: 100%;
            border-collapse: separate;
            border-spacing: 2px 5px;
        }

        .first {
            font-size: 25px;
            font-weight: bold;
        }

        td {
            text-align: center;
            background-color: #e5e5cc;
            padding: 0;
        }

        .create-i {
            float: right;
            height: 50px;
            width: 250px;
            font-size: 35px;
            color: white;
            background-color: #169ad4;
            cursor: pointer;
            margin-top: 10px;
            border: 0;
            border: none;
            border-radius: 25px;
        }

        .create-i:hover{
            background-color: #66cc66;
        }

        .create-i2{
            visibility: hidden;
            float: left;

            height: 50px;
            width: 250px;
            border: 0;
            border: none;
            border-radius: 25px;
        }
    </style>
    <%
        ArrayList<Team> teams= (ArrayList<Team>) request.getAttribute("team");
    %>
</head>
<body>
<h1>您当前还未加入团队！</h1>
<form action="createNewTeam" style="float: right">
<input class="create-i" type="submit" value="创建新团队">
</form>
<input class="create-i2" type="submit" value="创建新团队">
<h2>团队列表</h2>
<table>
    <tr class="first">
        <td>团队名称</td>
        <td>团队成员数</td>
        <td>团队简介</td>
        <td>加入团队</td>
    </tr>
    <%
        int id=0;
        for (Team team:teams){
    %>

    <tr class="content">
        <td class="nameList" onclick="showTeamDetails(this)"><%=team.getTeamName()%></td>
        <td><%=team.getTeamNum()%></td>
        <td><%=team.getTeamIntro()%></td>
        <td><a id="<%=id%>" href="joinTeam?realName=${cookie.web_realName.value}&stuId=${cookie.web_status2.value}&teamName=<%=team.getTeamName()%>" onclick="updateTeamName(this)">加入团队</a></td>
    </tr>

    <%
            id++;
        }
    %>
</table>
</body>
<script type="text/javascript" language="JavaScript">
    var nameList=document.getElementsByClassName("nameList");
    function updateTeamName(target) {
        document.cookie="web_teamName="+nameList[target.id].innerText+";path=/webLab";
    }

    function showTeamDetails(target) {
        location.href=location.href.split("teams-label")[0]+"showTeamDetail?teamName="+target.innerText;
    }
</script>
</html>
