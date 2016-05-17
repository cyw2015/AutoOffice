<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>资源管理</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div id="resTable_tool" style="padding:5px;">
   		<div style="margin-bottom:5px">
   		<sec:authorize access="hasRole('ROLE_RES_SYS_RES')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="resTable_tool.add();">增加资源</a>
   		</sec:authorize>
   		<sec:authorize access="hasRole('ROLE_RES_SYS_RES_UPDATE')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="resTable_tool.edit();">修改资源</a>
   		</sec:authorize>
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="resTable_tool.remove();">删除</a>
   		</div>
   	</div>
	<table id="resTable"></table>
	<!-- 新增 -->
 	 <form id="resRow_add" style="margin:0;padding:5px 0 0 25px;display:none">
		<p>账户:<input type="text"  name="test" style="width:200px;" /></p>
   		<p>密码:<input type="text"  name="password_add" style="width:200px;" /></p>
	</form>
	<script type="text/javascript" src="${staticPath }/static/js/sys/resource.js"></script>
</body>
</html>
