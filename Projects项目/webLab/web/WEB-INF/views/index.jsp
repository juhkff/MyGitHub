<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2018/8/1
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>外包平台</title>
    <link rel="stylesheet" type="text/css" href="../css/index.css">
</head>
<body>
<div class="container">
    <div class="textContainer">
        <div class="top_box">
            <div class="text_box">用户登录</div>
        </div>
        <div class="center_box">
            <form method="POST" action="Login" style="width: 100%;height: 100%;display: flex;flex-direction: column;align-self: stretch;justify-content: space-between">
                <div class="center_blank_box_1"></div>
                <input type="text" placeholder="用户ID" name="userId" id="user-id-label"
                       onkeyup="value=value.replace(/[\W]/g,'')" />
                <div class="center_blank_box_2">用户账号不存在</div>
                <input type="password" placeholder="密码" name="passWord" id="user-password-label" />
                <div class="center_blank_box_1" id="user-password-value">密码错误，请重新输入</div>
                <input type="submit" id="user-login" value="用户登录">
            </form>
        </div>
        </form>
        <div class="center_To_bottom_blank"></div>
        <div class="bottom_box">
            <div class="bottom_blank"></div>
            <div class="bottom_text bottom_blank"><a href="Forget">忘记账号</a></div>
            <div class="bottom_blank"></div>
            <div class="bottom_text bottom_blank"><a href="Register">新用户注册</a></div>
            <div class="bottom_blank"></div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var textField=document.getElementsByClassName("center_blank_box_2")[0];
    var textField2=document.getElementById("user-password-value");
        if("${result}"==="1"){
            textField.style.opacity=1;
            textField2.style.opacity=0;
        }else {
            document.getElementById("user-id-label").value="${userId}";
            textField.style.opacity=0;
            textField2.style.opacity=1;
        }
</script>
</html>
