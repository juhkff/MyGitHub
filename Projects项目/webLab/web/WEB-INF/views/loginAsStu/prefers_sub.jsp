<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 8/15/2018
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>显示偏好</title>
    <style type="text/css" rel="stylesheet">
        html{
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
        }
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;

            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
        }

        div{
            /*display: inline-block;*/
            font-size: 30px;
            /*background-color: #77df77;*/      /*为了解决Firefox的bug删除了*/
            /*width: 150px;*/
            min-width: 150px;
            /*height: 50px;*/
            /*min-height: 50px;*/
            max-height: 50px;
            margin: 20px;

            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
    <%
        String[] preferList= (String[]) request.getAttribute("preferList");
    %>
</head>
<body onclick="removeFirefoxSideEffect()">
<%
    for (String each:preferList){
%>
<div><%=each%></div>
<%
    }
%>
</body>
<script type="text/javascript" language="JavaScript">
    var divList=document.getElementsByTagName("div");
    for(var i=0;i<divList.length;i++){
        if (divList[i].id) {

        }else {
            divList[i].style.backgroundColor="#77df77";
        }
    }
    function removeFirefoxSideEffect() {
        var checkBox=document.getElementById("SL_balloon_obj");
        if (checkBox) {
            // checkBox.parentElement.removeChild(checkBox);
            checkBox.style.display="none";
            document.getElementById("SL_shadow_translation_result2").style.display="none";
        }
    }
</script>
</html>
