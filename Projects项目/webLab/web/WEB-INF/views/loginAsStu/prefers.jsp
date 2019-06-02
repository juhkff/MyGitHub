<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/15/2018
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>偏好设置</title>
    <style type="text/css" rel="stylesheet">
        * {
            font-family: "Microsoft YaHei UI Light";
        }

        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;

            display: flex;
            flex-direction: column;
            /*justify-content: center;*/
            align-items: left;
        }

        h1 {
            margin: 0;
            text-align: center;
            font-size: 50px;
            border-bottom: solid 1px black;
            margin-left: auto;
            margin-right: auto;
            display: block;
            width: 100%;
        }

        h2{
            margin: 0;
            text-align: center;
            font-size: 40px;
            border-bottom: solid 1px black;
            margin-left: auto;
            margin-right: auto;
            display: block;
            width: 100%;
        }

        .footer {
            position: absolute;
            bottom: 10px;
            right: 0px;
            left: 0px;
            height: 80px;
        }

        .footer > form{
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: row;
            justify-content: left;
            align-items: center;
            width: 100%;
            height: 30px;
        }

        .i_refers{
            padding: 0;
            margin: 0;
            border: solid 1px black;
            flex-grow: 8;
            height: 100%;
            font-size: 20px;
            margin-right: 25px;
        }

        .i_input{
            padding: 0;
            margin: 0;
            border: none;
            flex-grow: 1;
            height: 30px;
            font-size: 20px;
            margin-right: 25px;
            background-color: #169ad4;
            color: white;
            cursor: pointer;
            border-radius: 5px;
        }

        .i_input:hover{
            color: #169ad4;
            background-color: #cbcbcb;
        }

        h3{
            margin: 0;
            margin-bottom: 10px;
            margin-top: 10px;
            font-size: 20px;
        }

        iframe{
            width: 100%;
            flex-grow: 1;
            margin-top: 5px;
            margin-bottom: 80px;
        }
    </style>
    <%
        /*String[] preferList= (String[]) request.getAttribute("preferList");
        request.getSession().setAttribute("preferList",preferList);*/
        String preferString= (String) request.getAttribute("preferString");
    %>
</head>
<body>
<h1>您的偏好设置</h1>
<h2>选择您倾向于接受的外包类型标签</h2>
<iframe src="prefers-sub?userId=${cookie.web_userId.value}&userName=${cookie.web_userName.value}" frameborder="0">

</iframe>
<div class="footer">
    <h3>标签：</h3>
    <form id="r-form" action="uploadRefers">
        <input style="display: none" type="text" name="userId" value="${cookie.web_userId.value}">
        <input style="display: none" type="text" name="userName" value="${cookie.web_userName.value}">
        <textarea class="i_refers" name="referList" placeholder="暂无标签" form="r-form" rows="1"><%=preferString%></textarea>
        <%--<input class="i_refers" type="text" name="referList" placeholder="暂无标签" value="<%=preferString%>">--%>
        <input class="i_input" type="submit" value="提交修改">
    </form>
</div>
</body>
</html>
