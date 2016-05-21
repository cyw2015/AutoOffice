<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>岗位管理</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div id="posiTable_tool" style="padding:5px;">
   		<div style="margin-bottom:5px">
   		<sec:authorize access="hasRole('ROLE_RES_PER_POSI_ADD')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="posiTable_tool.add();">增加岗位</a>
   		</sec:authorize>
   		<sec:authorize access="hasRole('ROLE_RES_PER_POSI_EDIT')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="posiTable_tool.edit();">修改岗位</a>
   		</sec:authorize>
   		<sec:authorize access="hasRole('ROLE_RES_PER_POSI_DELETE')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="posiTable_tool.remove();">删除</a>
   		</sec:authorize>
   		</div>
   	</div>
	<table id="posiTable"></table>
	<!-- 添加 -->
	<form id="posiTable_add" style="display:none">
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 			<td>
	 			<p style="margin:10px 0px">名称<span style="color:red;">*</span>: <input type="text" id="posiTable_add_posi_name" name="posi_name"></p>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td >
	 			<p style="margin:10px 0px">级别<span style="color:red;">*</span>: <input type="text" id="posiTable_add_posi_level"  name="posi_level" /></p>		
	 			</td>
	 		</tr>
	 		<tr>
	 			<td >
	 			<p style="margin:10px 0px">描述: <input id="posiTable_add_posi_desc"></p> 
	 			</td>
	 		</tr>
	 	</table>
	</form>
	
	<!-- 修改 -->
	<form id="posiTable_edit" style="display:none">
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 			<td>
	 			<p style="margin:10px 0px">名称<span style="color:red;">*</span>: <input type="text" id="posiTable_edit_posi_name" name="posi_name"></p>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td >
	 			<p style="margin:10px 0px">级别<span style="color:red;">*</span>: <input type="text" id="posiTable_edit_posi_level"  name="posi_level" /></p>		
	 			</td>
	 		</tr>
	 		<tr>
	 			<td >
	 			<p style="margin:10px 0px">描述: <input id="posiTable_edit_posi_desc" name="posi_desc"></p> 
	 			</td>
	 		</tr>
	 	</table>
	</form>
	<script type="text/javascript" src="${staticPath }/static/js/person/position.js"></script>
</body>
</html>