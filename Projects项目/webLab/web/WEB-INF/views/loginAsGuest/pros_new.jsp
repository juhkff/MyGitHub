<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/14/2018
  Time: 3:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发布新外包</title>
    <style type="text/css" rel="stylesheet">
        html{
            margin: 0;
            padding: 0;
            width: 100%;
        }
        body{
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            background-color: #d6d6d6;
        }

        form{
            display: flex;
            flex-direction: column;
            justify-content: left;
            margin-top: 10px;
            margin-bottom: 0;
        }

        input{
            margin: 0;
            padding: 0;
        }

        .title-div{
            display: flex;
            flex-direction: row;
            padding-bottom: 10px;
            border-bottom: solid 1px black;
        }

        .title-div > h1{
            margin: 0;      /*消除firefox的特殊性*/
            font-size: 40px;
            margin-left: 20px;
        }

        .title-div > input{
            /*flex-grow: 7;*/
            flex-grow: 1;
            margin-right: 20px;
            font-size: 35px;
        }

        .refers-div{
            display: flex;
            flex-direction: column;
            padding-bottom: 10px;
            border-bottom: dashed 2px black;
        }

        .refers-div > h1{
            margin-left: 20px;
            margin-top: 5px;
            margin-bottom: 5px;
        }

        .refers-div > input{
            margin-left: 20px;
            margin-right: 20px;
            flex-grow: 1;
            font-size: 25px;
        }

        .content-div{
            display: flex;
            flex-direction: column;
            padding-bottom: 10px;
            /*border-bottom: dashed 2px black;*/
        }

        .content-div > h1{
            margin-left: 20px;
            margin-top: 5px;
            margin-bottom: 5px;
        }

        .content-div > textarea{
            margin-left: 20px;
            margin-right: 20px;
            flex-grow: 1;
            font-size: 25px;
        }

        .time-div, .reward-div{
            display: flex;
            flex-direction: row;
            align-items: center;
            padding-bottom: 5px;
            padding-right: 20px;
            /*border-bottom: dashed 2px black;*/
        }

        .time-div > h1, .reward-div > h1{
            margin: 0;
            margin-left: 20px;
            margin-top: 5px;
        }

        .time-div > input, .reward-div > input{
            width: 450px;
            height: 35px;
            margin-top: 5px;
            font-size: 25px;
            flex-grow: 1;
        }

        .submit-div{
            padding-top: 15px;
            padding-bottom: 10px;
            text-align: center;
            vertical-align: center;
        }

        .submit-div > input{
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

        .submit-div > input:hover{
            color: #169ad4;
            background-color: #cbcbcb;
        }
    </style>
</head>
<body>
<form action="newOne" method="post" id="pros-form">
    <div class="title-div">
        <h1>外包名称：</h1>
        <input type="text" name="pro_name" placeholder="输入外包名称">
    </div>
    <div class="refers-div">
        <h1>标签：</h1>
        <input type="text" name="pro_refers" placeholder="输入标签（多个标签之间用空格分隔）">
    </div>
    <div class="content-div">
        <h1>内容介绍：</h1>
        <textarea name="pro_content" placeholder="外包内容/外包任务" form="pros-form" rows="8"></textarea>
    </div>
    <div class="time-div">
        <h1>规定完成时间：</h1>
        <input name="pro_deadLine" placeholder="规定完成时间" type="text">
    </div>
    <div class="reward-div">
        <h1>报酬：</h1>
        <input name="pro_reward" placeholder="金额或其它形式" type="text">
    </div>
    <input name="user_name" type="text" value="${cookie.web_userName.value}" style="display: none">
    <input name="user_phoneNum" type="number" value="${cookie.web_phoneNum.value}" style="display: none">
    <div class="submit-div">
        <input type="submit" value="发布">
    </div>
</form>
</body>
</html>
