<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户登录</title>
<%@ include file="/commons/basejs.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${staticPath }/static/css/login.css">
	<script type="text/javascript" src="${staticPath }/static/js/login.js"></script>

</head>

<body>
	<div class="top_div"></div>
	<div
		style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">
		<form method="post" id="loginform" action="login">
			<div style="width: 165px; height: 96px; position: absolute;">
				<div class="tou"></div>
				<div class="initial_left_hand" id="left_hand"></div>
				<div class="initial_right_hand" id="right_hand"></div>
			</div>
			<p style="padding: 30px 0px 10px; position: relative;">
				<span class="u_logo"></span> 
				<input class="ipt" type="text" name="username" placeholder="请输入用户名" value="" />
			</p>
			<p style="position: relative;">
            	<span class="p_logo"></span>
            	<input class="ipt" id="password" type="password" name="password" placeholder="请输入密码" value="" />
       		 </p>
       		 <br>
       		 <p style="position: relative;color:red">
       		 	${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }
       		 </p>
       		 <div style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
       		 	<p style="margin: 0px 35px 20px 45px;">
       		 		<span style="float: left;">
                   		<a style="color: rgb(204, 204, 204);" href="javascript:;">忘记密码?</a>
                	</span>
                	<span style="float: right;">
                   		<a style="color: rgb(204, 204, 204); margin-right: 10px;" href="javascript:;">注册</a>
                    	<input type="submit" style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;" value="登录">
                	</span>
       		 	</p>
       		 </div>
		</form>
	</div>
	<br>
	<div style="text-align:center;">
		<p>残影悟</p>
	</div>
</body>
</html>
