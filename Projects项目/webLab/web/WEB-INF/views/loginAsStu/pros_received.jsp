<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Project_In_Published" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.model.Project_In_Accepted" %><%--
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
    <title>已接外包</title>
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
        ArrayList<Project_In_Accepted> project_in_accepteds = (ArrayList<Project_In_Accepted>) request.getAttribute("pros");
    %>
</head>
<body>
<h1>外包列表</h1>
<table>
    <%--外包名称; 发布时间; 规定完成时间; 操作1; 详细信息--%>
    <tr class="first">
        <td>外包名称</td>
        <td>外包提供人</td>
        <td>规定完成时间</td>
        <td>外包形式</td>
        <td>操作1</td>
        <td>操作2</td>
        <td>外包状态</td>
    </tr>
    <%
        int i = 0;
        String curType="Error";
        for (Project_In_Accepted project_in_accepted : project_in_accepteds) {
            curType=project_in_accepted.getCurrentType();
            if (curType.equals("1"))
                curType="正在进行";
            else if (curType.equals("2"))
                curType="已提交，待处理";
            else if (curType.equals("3"))
                curType="被驳回";
    %>
    <tr class="content">
        <td class="each" id="<%=i%>" onclick="showDetails(this)"><%=project_in_accepted.getProName()%>
        </td>
        <td class="f_each" id="f_<%=i%>" onclick="showUser(this)"><%=project_in_accepted.getPublicUser()%>
        </td>
        <td><%=project_in_accepted.getDeadLine()%>
        </td>
        <td><%=(project_in_accepted.getProType().equals("1")) ? "个人" : "团队"%></td>
        <td>
            <a href="../pros/quit?userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}&proName=<%=project_in_accepted.getProName()%>">放弃该外包</a>
        </td>
        <td><a class="a_each" href="../pros/submit?userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}&proName=<%=project_in_accepted.getProName()%>">提交外包</a></td>
        <td><%=curType%></td>
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

    function showUser(target){
        location.href=location.href.split("login")[0]+"userInfo?userName="+document.getElementById(target.id).innerText;
    }
</script>
</html>

