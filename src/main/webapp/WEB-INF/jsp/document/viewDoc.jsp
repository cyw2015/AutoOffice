<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>公文发布</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div id="viewDocTable_tool" style="padding:5px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="viewDocTable_tool.lookDoc();">查看详情</a>
	</div>
	<table id="viewDocTable"></table>
	<div id="viewDocDetail" style="display:none" ></div>
  	<script type="text/javascript" src="${staticPath }/static/js/document/viewDoc.js"></script>
</body>
</html>