<%@ page import="com.model.Project_In_Finished" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Project_In_Completed" %><%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/14/2018
  Time: 11:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>已结束的外包</title>
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
    </style>
    <%
        ArrayList<Project_In_Completed> project_in_completeds = (ArrayList<Project_In_Completed>) request.getAttribute("pros");
    %>
</head>
<body>
<h1>外包列表</h1>
<table>
    <%--外包名称; 发布时间; 规定完成时间; 操作1; 详细信息--%>
    <tr class="first">
        <td>外包名称</td>
        <td>发布时间</td>
        <td>完成时间</td>
        <td>外包形式</td>
        <td>删除记录</td>
    </tr>
    <%
        int i=0;
        for (Project_In_Completed project_in_completed: project_in_completeds) {
    %>
    <tr class="content">
        <td class="each" id="<%=i%>" onclick="showDetails(this)"><%=project_in_completed.getProName()%>
        </td>
        <td><%=project_in_completed.getPublishTime()%>
        </td>
        <td><%=project_in_completed.getFinishTime()%>
        </td>
        <td><%=(project_in_completed.getProType().equals("1"))?"个人":"团队"%>-<%=project_in_completed.getReceiverName()%></td>
        <td><a href="">删除记录（此功能被取消）</a></td>
    </tr>
        <%
                i++;
        }%>
</table>
</body>
<script type="text/javascript" language="JavaScript">
    function showDetails(target) {
        location.href=location.href.split("login")[0]+"pros/details?proName="+document.getElementById(target.id).innerText;
    }
</script>
</html>
