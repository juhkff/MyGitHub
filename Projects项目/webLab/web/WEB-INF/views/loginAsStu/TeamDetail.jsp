<%@ page import="com.model.Team" %>
<%@ page import="java.lang.reflect.Type" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="com.model.Student_In_Team" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %><%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/15/2018
  Time: 5:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>团队详情</title>
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
            /*border-bottom: solid 1px black;*/
        }

        textarea{
            font-size: 20px;
            width: 100%;
            height: 250px;
        }

        h3{
            text-align: center;
            font-size: 30px;
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
    </style>
    <%
        Team team= (Team) request.getAttribute("team");
        String teamMemberString=team.getTeamMember();
        Type type=new TypeToken<ArrayList<Student_In_Team>>(){}.getType();
        ArrayList<Student_In_Team> student_in_teams=new GsonBuilder().enableComplexMapKeySerialization().create().fromJson(teamMemberString,type);
    %>
</head>
<body>
<h1>一次只能加入一个团队！</h1>
<%--<h2>团队信息</h2>--%>
<h2>团队名称:<%=team.getTeamName()%></h2>
<textarea placeholder="暂无介绍" readonly="readonly">团队介绍:<%=team.getTeamIntro().equals("")?"暂无":team.getTeamIntro()%></textarea>
<table>
    <tr>
        <td colspan="3"><h3 class="first">队长:<%=team.getTeamLeader()%></h3></td>
    </tr>
    <tr>
        <td>真实姓名</td>
        <td>学号</td>
        <td>详细信息</td>
    </tr>
    <%
        for (Student_In_Team student_in_team:student_in_teams){
    %>

    <tr>
        <td><%=student_in_team.getRealName()%></td>
        <td><%=student_in_team.getStuId()%></td>
        <td><a href="../userInfo2?realName=<%=student_in_team.getRealName()%>">点击查看</a></td>
    </tr>

    <%
        }
    %>
</table>
<%--<form action="quitTeam" method="">
    <input type="submit" value="退出团队">
</form>--%>
</body>
</html>
