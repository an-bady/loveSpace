<%--
  Created by IntelliJ IDEA.
  User: wangsiming
  Date: 18/2/3
  Time: 上午9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>💗 登陆 思淋空间 💗</title>

</head>
<body style="background-image: url(/resources/img/back.jpeg)">


<div id="div" style="margin-left:40%;margin-top:10%;width:30%; height:30%; border:0px solid #000;">


    <span style="color:palevioletred;font-weight:bolder;font-size:25px">欢迎登录思淋空间!</span>
    <br>
    <br>

    <form action="loginServlet" method="post">
        账号： <input type="text" name="name"><br>
        密码： <input type="password" name="password"><br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" value="登陆">
    </form>

    <a href="/views/login/register.jsp">
        <span style="color:palevioletred;font-weight:bolder;font-size:15px">😄 没有账号？先注册吧 😄</span></a>
</div>

</body>
</html>
