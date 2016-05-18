<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>权限管理</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div id="authTable_tool" style="padding:5px;">
   		<div style="margin-bottom:5px">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="authTable_tool.add();">增加权限</a>
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="authTable_tool.edit();">修改权限</a>
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="authTable_tool.remove();">删除权限</a>
   		</div>
   	</div>
	<table id="authTable"></table>
	<!-- 新增 -->
	<form id="authTable_add" style="display:none">
		<table style="align:'center';height:100%;width:100%;padding:0px 10px;">
			<tr>
				<td><label for="authTable_add_auth_code">权限编码<span style="color:red;">*</span>:</label></td>
				<td><input type="text" id="authTable_add_auth_code" name="authority_code"></td>
			</tr>
			<tr>
				<td><label for="authTable_add_auth_name">权限名称<span style="color:red;">*</span>:</label></td>
				<td><input type="text" id="authTable_add_auth_name" name="authority_name"></td>
			</tr>
			<tr>
				<td><label for="authTable_add_remark">备 注 :</label> </td>
				<td><input type="text" id="authTable_add_remark" name="remark"></td>
			</tr>
		</table>
	</form>
	
	<form id="authTable_edit" style="display:none">
		<table style="align:'center';height:100%;width:100%;padding:0px 10px;">
			<tr>
				<td><label for="authTable_edit_auth_code">权限编码<span style="color:red;">*</span>:</label></td>
				<td><input type="text" id="authTable_edit_auth_code" name="authority_code"></td>
			</tr>
			<tr>
				<td><label for="authTable_edit_auth_name">权限名称<span style="color:red;">*</span>:</label></td>
				<td><input type="text" id="authTable_edit_auth_name" name="authority_name"></td>
			</tr>
			<tr>
				<td><label for="authTable_edit_remark">备 注 :</label> </td>
				<td><input type="text" id="authTable_edit_remark" name="remark"></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript" src="${staticPath }/static/js/sys/auth.js"></script>
</body>
</html>
