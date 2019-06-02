<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Project_In_Published" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.model.Project_In_Accepted" %>
<%@ page import="com.model.Project_In_Finished" %>
<%@ page import="com.model.Project_In_Show" %><%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/13/2018
  Time: 10:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>外包总览</title>
    <style type="text/css" rel="stylesheet">
        * {
            font-family: "Microsoft YaHei UI Light";
        }

        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
        }

        h1 {
            margin: 0;
            text-align: center;
            background-color: #3c3f41;
        }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 2px 5px;
        }

        td {
            text-align: center;
            background-color: #e5e5cc;
            padding: 0;
        }

        .first {
            font-size: 25px;
            font-weight: bold;
        }

        .each {
            cursor: pointer;
        }

        .each:hover {
            background-color: #77df77;
        }

        textarea {
            height: 45px;
            width: 99%;
            background-color: #e5e5cc;
            border: none;
        }

        .search-div {
            width: 100%;
            height: 50px;
            /*background-color: #989898;*/
            margin-top: 5px;
            margin-bottom: 5px;
            display: flex;
            flex-direction: row;
            justify-content: left;
            align-items: center;
        }

        .search-div-i {
            height: 48px;
            padding: 0;
            border: solid 1px black;
            border-radius: 24px;
            margin-top: 1px;
            flex-grow: 1;
            font-size: 30px;
            margin-right: 5px;
            padding-left: 15px;
        }

        .search-div-si {
            height: 48px;
            padding: 0;
            border: 0;
            margin-top: 1px;
            width: 200px;
            margin-right: 5px;
            border-radius: 24px;

            background-color: #1699d3;
            color: white;
            cursor: pointer;
            font-size: 30px;
        }
        .search-div-si:hover{
            background-color: #66cc66;
            color: white;
        }
    </style>
    <%
        ArrayList<Project_In_Show> project_in_shows = (ArrayList<Project_In_Show>) request.getAttribute("pros");
    %>
</head>
<body>
<form action="searchPros" class="search-div" method="get">
    <div style="width: 100px;height: 100%;display: flex;justify-content: center;align-items: center;font-size: 30px">搜索:</div>
    <input class="search-div-i" type="text" name="proTags" placeholder="按标签搜索，每个标签之间要有空格">
    <input class="search-div-si" type="submit" value="搜索">
</form>
<h1>外包列表</h1>
<table>
    <%--外包名称; 发布时间; 规定完成时间; 操作1; 详细信息--%>
    <tr class="first">
        <td>外包名称</td>
        <td>外包标签</td>
        <td>外包提供人</td>
        <td>内容简介</td>
        <td>详细信息</td>
    </tr>
    <%
        int i = 0;
        for (Project_In_Show project_in_show: project_in_shows) {
    %>
    <tr class="content">
        <td id="<%=i%>"><%=project_in_show.getProName()%>
        </td>
        <td><%=project_in_show.getProTags()%>
        </td>
        <td class="each f_each" id="f_<%=i%>" onclick="showUser(this)"><%=project_in_show.getUserName()%>
        </td>
        <td><textarea rows="1"><%=project_in_show.getProContent()%></textarea></td>
        <td><a class="a_each" href="../pros/details2?proName=<%=project_in_show.getProName()%>">详细信息</a></td>
    </tr>
        <%
                i++;
        }%>
</table>
</body>
<script type="text/javascript" language="JavaScript">
    function showUser(target){
        location.href=location.href.split("login")[0]+"userInfo?userName="+document.getElementById(target.id).innerText;
    }
</script>
</html>

