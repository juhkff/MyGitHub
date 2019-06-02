<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/13/2018
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>找回账号</title>
    <style type="text/css" rel="stylesheet">
        html{
            background-color: grey;
            height: 100%;
        }
        body{
            width: 90%;
            margin: auto;
            background-color: white;
            height: 100%;
        }
        form{
            width: 900px;
            margin: auto;
            margin-top: 25px;
        }
        #text{
            width: 700px;
            height: 40px;
            padding: 0;
            margin: 0;
        }
        #submit{
            width: 150px;
            height: 43px;
        }

        iframe{
            margin-top: 25px;
            width: 100%;
            height: 500px;
        }

        .back{
            margin: 0;
            position: absolute;
        }
    </style>
    <%
//        session.setAttribute("userList",request.getAttribute("userList"));
    %>
</head>
<body>
<div style="width: 100%;height: 75px;background-color: white"></div>
<h1 class="back"><a href="../index.jsp">返回登录界面</a></h1><h1 style="text-align: center; margin-top: 0">找回账号</h1>
<form action="searchUserList" method="get">
    <input id="text" type="text" name="userInfo" <%--value="juhkaf"--%> placeholder="输入你的用户名，或是你的企业/真实姓名 等任何关于你的注册时登记的信息.多个关键字之间请用空格分隔.">
    <input id="submit" type="submit" value="搜索">
</form>
<iframe src="showUserList" frameborder="0"></iframe>
</body>
</html>
