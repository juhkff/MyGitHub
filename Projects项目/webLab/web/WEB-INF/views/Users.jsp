<%@ page import="com.model.EasyUser" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/13/2018
  Time: 5:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户列表</title>
    <style rel="stylesheet" type="text/css">
        body {
            width: 100%;
            height: 100%;
            margin: 0;
            background-color: #3c3f41;
        }

        table {
            width: 100%;
            /*height: 100%;*/
            border-collapse: separate;
            border-spacing: 0px 10px;
            margin-top: -9px;
        }

        td{
            text-align: center;
            background-color: white;
            font-size: 25px;
            border: solid 1px #e5e5cc;
        }
    </style>
    <%
        ArrayList<EasyUser> easyUsers = (ArrayList<EasyUser>) session.getAttribute("userList");
    %>
</head>
<body>
<table>
    <tr>
        <td>账号</td>
        <td>用户名</td>
        <td>手机号码</td>
        <td>电子邮箱</td>
        <td>学校/企业</td>
        <td>学号/企业地址</td>
        <td>真实姓名</td>
    </tr>
    <%
        if (easyUsers != null) {
            for (EasyUser each : easyUsers) {
    %>
    <tr>
        <td><%=each.getUserId()%>
        </td>
        <td><%=each.getUserName()%>
        </td>
        <td><%=each.getPhoneNum()%>
        </td>
        <td><%=each.getEmailAddress()%>
        </td>
        <td><%=each.getStatus1()%>
        </td>
        <td><%=each.getStatus2()%>
        </td>
        <td><%=each.getRealName()%>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
<script type="text/javascript" language="JavaScript">
</script>
</html>
