<%@ page import="com.model.Project_In_Finished" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Project_In_Completed" %>
<%@ page import="com.model.Project_In_Submit" %><%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/14/2018
  Time: 11:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>已提交待审核的外包</title>
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
        ArrayList<Project_In_Submit> project_in_submits = (ArrayList<Project_In_Submit>) request.getAttribute("pros");
    %>
</head>
<body>
<h1>提交列表</h1>
<table>
    <%--外包名称; 发布时间; 规定完成时间; 操作1; 详细信息--%>
    <tr class="first">
        <td>外包名称</td>
        <td>申请者</td>
        <td>外包形式</td>
        <td>规定完成时间</td>
        <td>提交时间</td>
        <td>操作</td>
    </tr>
    <%
        int i=0;
        for (Project_In_Submit project_in_submit: project_in_submits) {
    %>
    <tr class="content">
        <td class="each" id="<%=i%>" onclick="showDetails(this)"><%=project_in_submit.getProName()%>
        </td>
        <td><%=project_in_submit.getReceiverName()%>
        </td>
        <td><%=(project_in_submit.getProType().equals("1"))?"个人":"团队"%>
        </td>
        <td><%=project_in_submit.getDeadLine()%></td>
        <td><%=project_in_submit.getSubmitTime()%></td>
        <td><a href="../pros/agree?proName=<%=project_in_submit.getProName()%>&userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}">同意</a><br><<a href="">拒绝（无此功能）</a>></td>
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
