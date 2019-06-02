<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Project_In_Published" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.model.Project_In_Request" %><%--
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
    <title>正在进行的外包</title>
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
            font-size: 50px;
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

        form {
            position: absolute;
            top: 2px;
            left: 10px;
        }

        form > input{
            width: 300px;
            height: 60px;
            border-radius: 30px;
            border: none;
            font-size: 35px;
            font-weight: bold;
            color: white;
            background-color: #169ad4;
            cursor: pointer;
        }

        form > input:hover{
            color: #169ad4;
            background-color: #cbcbcb;
        }

    </style>
    <%
        ArrayList<Project_In_Request> project_in_requests= (ArrayList<Project_In_Request>) request.getAttribute("pros");
    %>
</head>
<body>
<h1>正在进行的外包列表</h1>
<form action="request-label" method="get">
    <input style="display: none" type="text" name="userId" value="${cookie.web_userId.value}">
    <input style="display: none" type="text" name="userName" value="${cookie.web_userName.value}">
    <input type="submit" value="返回申请列表">
</form>
<table>
    <%--外包名称; 发布时间; 规定完成时间; 操作1; 详细信息--%>
    <tr class="first">
        <td>外包名称</td>
        <td>申请者</td>
        <td>申请外包形式</td>
        <td>操作</td>
        <td>详细信息</td>
    </tr>
    <%
        int i=0;
        for (Project_In_Request project_in_request: project_in_requests) {
    %>
    <tr class="content">
        <td class="each" id="<%=i%>" onclick="showDetails(this)"><%=project_in_request.getProName()%>
        </td>
        <td><%=project_in_request.getReceiverName()%>
        </td>
        <td><%=(project_in_request.getProType().equals("1"))?"个人":"团队"%>
        </td>
        <td>
            <%--<a href="../pros/pass?proName=<%=project_in_request.getProName()%>&userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}">同意</a>
            <a href="../pros/reject?proName=<%=project_in_request.getProName()%>&userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}">拒绝</a>--%>
            暂无
        </td>
        <td><a class="a_each" href="../pros/details?proName=<%=project_in_request.getProName()%>">详细信息</a></td>
    </tr>
        <%
                i++;
        }%>
</table>
</body>
<script type="text/javascript" language="JavaScript">
    var alist = undefined;
    alist = document.getElementsByClassName("a_each");
    function showDetails(target) {
        location.href=alist[target.id].href;
    }
</script>
</html>

