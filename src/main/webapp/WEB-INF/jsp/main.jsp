<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>主界面</title>
<%@ include file="/commons/basejs.jsp"%>
<script>
var ctx = '${staticPath }';
</script>
<style type="text/css">
	.logo {
		width:180px;
		height:50px;
		line-height:50px;
		text-align:center;
		font-size:20px;
		font-weigth:bold;
		float:left;
		color:#fff;
	}

	.logout {
		float:right;
		padding:30px 15px 0 0;
		color:#fff;
	}
	a {
		color:#fff;
		text-decoration:none;
	}
	a:hover {
		text-decoration:underline;
	}
</style>
<script type="text/javascript" src="${staticPath }/static/js/main.js"></script>
</head>
<body class="easyui-layout">
<!--头  -->
	<div data-options="region:'north',title:'North Title',split:true,noheader:true" 
	style="height:60px;background:#666;background-image:url(${staticPath }/static/images/head.png)">
		<div class="logo">OA管理系统</div>
		<div class="logout" >
		 	您好,<a href="logout">退出</a>
		</div>
	</div>
	<!-- 底 -->
	<div
		data-options="region:'south',title:'South Title',split:true,noheader:true"
		style="height:35px;line-height:30px;text-align:center">
		@2016年5月10日 残影悟</div>
		<!-- 侧边导航栏 -->
	<div
		data-options="region:'west',title:'导航栏',split:true,iconCls:'icon-world'"
		style="width:180px;padding:10px;">
		<ul id="nav"></ul>
	</div>
	<!-- 中央区域 -->
	<div data-options="region:'center'" style="overflow:hidden;">
		<div id="tabs">
		</div>
	</div>
	<!-- 菜单 -->
	 <div id="mm" class="easyui-menu" style="width: 160px;">
		<div id="mm-tabclose" name="6">刷新</div>
		<div id="Div1" name="1">关闭</div>
		<div id="mm-tabcloseall" name="2">全部关闭</div>
		<div id="mm-tabcloseother" name="3">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright" name="4">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft" name="5">当前页左侧全部关闭</div>
	</div>
</body>
</html>