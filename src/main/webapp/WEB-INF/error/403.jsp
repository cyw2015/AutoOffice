<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf8" />
<title>403</title>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	font: 14px/1.6 Arial, Sans-serif;
	background: #fff;
}

h1 {
	position: relative;
	z-index: 2;
	width: 540px;
	height: 0;
	margin: 110px auto 15px;
	padding: 230px 0 0;
	overflow: hidden;
	xxxxborder: 1px solid;
	background-image: url(${staticPath }/static/images/403.jpg);
	background-repeat: no-repeat;
}

h2 {
	position: absolute;
	top: 17px;
	left: 187px;
	margin: 0;
	font-size: 0;
	text-indent: -999px;
	-moz-user-select: none;
	-webkit-user-select: none;
	user-select: none;
	cursor: default;
	width: 534px;
}

h2 em {
	display: block;
	font: italic bold 200px/120px "Times New Roman", Times, Serif;
	text-indent: 0;
	letter-spacing: -5px;
	color: rgba(216, 226, 244, 0.3);
}

a:link,a:visited {
	color: #007ab7;
	text-decoration: none;
}

.link a {
	margin-right: 1em;
}

.link,.texts {
	width: 540px;
	margin: 0 auto 15px;
	color: #505050;
}

.texts {
	line-height: 2;
}

.texts dd {
	margin: 0;
	padding: 0 0 0 15px;
}

.texts ul {
	margin: 0;
	padding: 0;
}

.STYLE1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 65px;
}
</style>

</head>

<body>
	<h1></h1>
	<h2>
		<em><span class="STYLE1">403 Error&nbsp;&nbsp; </span></em>:所查找的页面,你没有权限访问!
	</h2>
	<p class="link">
		 <a href="${staticPath }/index.jsp">&#9666;返回首页</a>
	</p>
	<dl class="texts">
        <dt>你怎么可以没有权限呢?</dt>
<dd>
            <ul>
                <li>你求我啊!</li>
                <li>如果你求我的话...</li>
                <li>那我...</li>
                <li>也不会让你查看,嘿嘿!</li>
            </ul>
        </dd>
    </dl>
</body>
</html>
