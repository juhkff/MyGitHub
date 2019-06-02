<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/12/2018
  Time: 10:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户登录主页</title>
    <style rel="stylesheet" type="text/css">
        *{
            font-family: "Microsoft YaHei UI Light";
        }
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
        }

        h1 {
            border-left: solid #cbcbcb 1px;
            padding-left: 10px;
            margin-top: 5px;
            margin-bottom: 5px;
            display: inline;
        }

        span {
            vertical-align: bottom;
            color: #989898;
            margin-left: 5px;
        }

        .container {
            border: solid #989898 1px;
        }

        .container-left {
            float: left;
            /*background-color: rosybrown;*/
            height: 93%;
            width: 35%;
            display: flex;
            flex-direction: column;
            justify-content: left;
            align-items: center;
        }

        .container-right {
            float: left;
            /*background-color: #4183ff;*/
            height: 93%;
            width: 65%;
        }

        .container-left > img {
            width: 200px;
            height: 200px;
            border-radius: 100px;
        }

        .container-left > form > input , .container-left > input{
            width: 150px;
            height: 40px;
            border-radius: 10px;
            border-style: none;
            background-color: #169bd5;
            color: white;
            font-size: 23px;
            font-weight: bold;
            cursor: pointer;
            padding-top: 2px;
            padding-bottom: 5px;
        }

        .container-left > input:hover {
            background-color: #66cc66;
        }

        .container-left > form > input:hover {
            background-color: #66cc66;
        }

        .container-left > * {
            margin-top: 25px;
            margin-bottom: 25px;
        }


        .container-left > form > textarea {
            width: 300px;
            height: 25px;
            display: block;
            cursor: pointer;
        }

        .container-left > form {
            display: flex;
            flex-direction: column;
            justify-content: left;
            align-items: center;
        }

        .error-info {
            color: red;
            opacity: 0;
        }

        .h1-left{
            display: inline-block;
            /*margin-top: 50px;*/
            margin-top: 70px;
            margin-left: 25px;
            position: absolute;
            font-weight: bold;
            font-family: 思源黑体;
        }

        .h1-right{
            display: inline-block;
            /*margin-top: 50px;*/
            margin-top: 70px;
            margin-left: 395px;
            position: absolute;
            font-weight: bold;
            font-family: 思源黑体;
        }

        #input-info-submit{
            width: 150px;
            height: 40px;
            border-radius: 10px;
            border-style: none;
            background-color: #169bd5;
            color: white;
            font-size: 23px;
            font-weight: bold;
            cursor: pointer;
            margin-left: 160px;
            position: relative;
            top: 2px;
        }

        #input-info-submit:hover {
            background-color: #66cc66;
        }

        #input-real-submit{
            width: 150px;
            height: 40px;
            border-radius: 10px;
            border-style: none;
            background-color: #169bd5;
            color: white;
            font-size: 23px;
            font-weight: bold;
            cursor: pointer;
            margin-left: 150px;
            position: relative;
            top: 2px;
        }

        .form-left{
            position: absolute;
            margin-top: 70px;
            padding-left: 25px;
            border-right: dashed 1px black;
        }

        .form-right{
            position: absolute;
            margin-top: 70px;
            margin-left: 395px;
            /*border-right: dashed 1px black;*/
        }

        .basic-table{
            width: 365px;
            margin-top: 75px;
        }

        .basic-table-left{
            width: 145px;
            padding: 0;
            margin: 0;
            font-size: 20px;
            padding-top: 20px;
            padding-bottom: 20px;
            text-align: right;
        }

        .basic-table-right{
            width: 220px;
            padding: 0;
            margin: 0;
            text-align: left;
        }

        .basic-table-right > input{
            height: 30px;
            font-size: 20px;
            width: 220px;
            -moz-appearance:textfield;
        }

        .special-text {
            font-size: 20px;
        }

        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            appearance: none;
            margin: 0;
        }
    </style>
    <%
        Cookie userTypeC=null;
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies){
            if (cookie.getName().equals("web_userType")) {
                userTypeC = cookie;
                break;
            }
        }
        int userType=-1;
        if (userTypeC!=null)
            userType= Integer.parseInt(userTypeC.getValue());
    %>

    <%
        String status1=userType==0?"学校":"企业";
        String status2=userType==0?"学号":"企业地址";
    %>
</head>
<body>
<div>
    <h1 style="font-family: 黑体">个人资料</h1><span>查看和编辑个人信息</span>
</div>
<div style="border: solid 1px #989898 ;margin-top: 10px;">
    <div class="container-left">
        <img src="getImg" style="margin-top: 50px;cursor: pointer" onclick="getImg()">
        <input type="button" value="更换头像" onclick="document.getElementById('input-file').click()">
        <form action="uploadImg" method="post" enctype="multipart/form-data">
            <input type="file" name="file" id="input-file" style="display: none" onchange="getPath(this.value)">
            <div class="error-info">请上传有效的图片格式</div>
            <textarea readonly="readonly" style="margin-top: -50px" placeholder="选择图片路径/请自行裁剪为正方形后上传" onclick="document.getElementById('input-file').click()"></textarea>
            <input type="submit" value="提交" style="margin-top: 25px">
        </form>
    </div>
    <div class="container-right">
        <h1 class="h1-left">基本资料</h1>
        <form class="form-left" action="uploadInfo" method="post">
            <input id="input-info-submit" type="submit" value="提交修改" onclick="changeInfo()">
            <table class="basic-table">
                <tr><td class="basic-table-left">用户名：</td><td class="basic-table-right special-text" id="user-name-label" ><%--<input id="user-name-label" type="text" name="userName" value="--%>${cookie.web_userName.value}<%--">--%></td></tr>
                <tr><td class="basic-table-left">账号：</td><td class="basic-table-right special-text" id="user-id-label"><%--<input type="number" readonly="readonly" value="--%>${cookie.web_userId.value}<%--">--%></td></tr>
                <tr><td class="basic-table-left">手机号码：</td><td class="basic-table-right"><input id="user-phone-label" type="number" name="phoneNum" value="${cookie.web_phoneNum.value}"></td></tr>
                <tr><td class="basic-table-left">电子邮箱：</td><td class="basic-table-right"><input id="user-email-label" type="email" name="email" value="${cookie.web_email.value}"></td></tr>
            </table>
        </form>

        <h1 class="h1-right">实名信息</h1>
        <form class="form-right" action="uploadActual" method="post">
            <input id="input-real-submit" type="submit" value="提交修改" style="visibility: hidden">
            <table class="basic-table">
                <tr><td class="basic-table-left"><%=status1%>：</td><td class="basic-table-right  special-text" id="com-name-label"><%--<input class="special-text" type="text" readonly="readonly" value="--%>${cookie.web_status1.value}<%--">--%></td></tr>
                <tr><td class="basic-table-left"><%=status2%>：</td><td class="basic-table-right special-text" id="com-addr-label"><%--<input class="special-text" type="text" readonly="readonly" value="--%>${cookie.web_status2.value}<%--">--%></td></tr>
                <tr><td class="basic-table-left">真实姓名：</td><td class="basic-table-right special-text" id="user-real-label"><%--<input class="special-text" type="text" readonly="readonly" value="--%>${cookie.web_realName.value}<%--">--%></td></tr>
                <tr><td class="basic-table-left">身份证号码：</td><td class="basic-table-right special-text" id="user-card-label"><%--<input class="special-text" type="text" readonly="readonly" value="--%>${cookie.web_IDcard.value}<%--">--%></td></tr>
            </table>
        </form>
    </div>
    <div style="clear: both"></div>
</div>
</body>
<script type="text/javascript" language="JavaScript">
    if ('${upload}'==='true'){
        alert("更新头像成功!");
    }else if ('${upload}' === 'false') {
        alert("更新头像失败!");
    }
    
    if ('${result}' === '-1')
        alert("个人信息更新错误!");
    else if ('${result}' === '0')
        alert("更新成功!");
    // var input_f = document.getElementById("input-file");
    var textarea=document.getElementsByTagName("textarea")[0];

    var user_name_label=document.getElementById("user-name-label");
    var user_phone_label=document.getElementById("user-phone-label");
    var user_email_label=document.getElementById("user-email-label");

    function changeInfo() {
        document.cookie="web_userName="+user_name_label.innerText+";path=/webLab";
        document.cookie="web_phoneNum="+user_phone_label.value+";path=/webLab";
        document.cookie="web_email="+user_email_label.value+";path=/webLab";
    }
    
    function getPath(path){
        textarea.innerHTML=path;
        if (textarea.innerHTML.length >= 40) {
            var height=(textarea.innerHTML.length/40+1)*25;
            textarea.style.height=height+"px";
        }else {
            textarea.style.height="25px";
        }
    }

    function getImg(){
        window.open("getImg");
    }

    /*function uploadFile() {
        var imgpath = document.getElementById("input-file").value;    //这里只是字符串而已
        if (!imgpath.endsWith(".jpg") && !imgpath.endsWith(".png") && !imgpath.endsWith(".gif") && !imgpath.endsWith(".bmp") && !imgpath.endsWith(".jpeg")) {
            document.getElementsByClassName("error-info")[0].style.opacity = 1;
            return;
        } else {
            document.getElementsByClassName("error-info")[0].style.opacity = 0;
            var img = input_f.files[0];
            var form = new FormData();        //FormData对象
            form.append("file", img);

            var xhr = new XMLHttpRequest();   //XMLHttpRequest对象
            xhr.open("post", "login/uploadImg", true);  //post方式，url为服务器请求地址，true该参数规定请求是否异步处理.
            xhr.send(form);
        }
    }*/


</script>
</html>
