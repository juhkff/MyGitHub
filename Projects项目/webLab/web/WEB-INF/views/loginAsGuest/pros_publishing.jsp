<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Project_In_Published" %>
<%@ page import="java.net.URLEncoder" %><%--
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
    <title>在发布的外包</title>
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
        ArrayList<Project_In_Published> project_in_publisheds = (ArrayList<Project_In_Published>) request.getAttribute("pros");
    %>
</head>
<body>
<h1>外包列表</h1>
<table>
    <%--外包名称; 发布时间; 规定完成时间; 操作1; 详细信息--%>
    <tr class="first">
        <td>外包名称</td>
        <td>发布时间</td>
        <td>规定完成时间</td>
        <td>操作</td>
        <td>详细信息</td>
    </tr>
    <%
        int i=0;
        for (Project_In_Published project_in_published : project_in_publisheds) {
    %>
    <tr class="content">
        <td class="each" id="<%=i%>" onclick="showDetails(this)"><%=project_in_published.getProName()%>
        </td>
        <td><%=project_in_published.getPublishTime()%>
        </td>
        <td><%=project_in_published.getDeadLine()%>
        </td>
        <td>
            <a href="../pros/cancel?userName=${cookie.web_userName.value}&proName=<%=project_in_published.getProName()%>">取消发布</a>
        </td>
        <td><a class="a_each" href="../pros/details?proName=<%=project_in_published.getProName()%>">详细信息</a></td>
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

