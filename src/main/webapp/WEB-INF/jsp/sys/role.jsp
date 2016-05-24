<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>角色管理</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div id="roleTable_tool" style="padding:5px;">
   		<div style="margin-bottom:5px">
   		<sec:authorize access="hasRole('ROLE_RES_SYS_ROLE_ADD')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="roleTable_tool.add();">增加角色</a>
   		</sec:authorize>	
   		<sec:authorize access="hasRole('ROLE_RES_SYS_ROLE_EDIT')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="roleTable_tool.edit();">修改角色</a>
   		</sec:authorize>	
   		<sec:authorize access="hasRole('ROLE_RES_SYS_ROLE_CONFIG')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="roleTable_tool.config();">配置权限</a>
   		</sec:authorize>
   		<sec:authorize access="hasRole('ROLE_RES_SYS_ROLE_DELETE')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="roleTable_tool.remove();">删除角色</a>
   		</sec:authorize>
   		</div>
   	</div>
	<table id="roleTable"></table>
	<!-- 新增 -->
	<form id="roleTable_add" style="display:none">
	<table style="align:'center';height:100%;width:100%;padding:0px 10px;">
		<tr>
			<td><label for="roleTable_add_role_code">角色编码<span style="color:red;">*</span>:</label></td>
			<td><input type="text" id="roleTable_add_role_code" name="role_code"></td>
		</tr>
		<tr>
			<td><label for="roleTable_add_role_name">角色名称<span style="color:red;">*</span>:</label></td>
			<td><input type="text" id="roleTable_add_role_name" name="role_name"></td>
		</tr>
		<tr>
			<td><label for="roleTable_add_remark">备 注 :</label> </td>
			<td><input type="text" id="roleTable_add_remark" name="remark"></td>
		</tr>
	</table>
	</form>
	
	<!-- 修改 -->
	<form id="roleTable_edit" style="display:none">
	<table style="align:'center';height:100%;width:100%;padding:0px 10px;">
		<tr>
			<td><label for="roleTable_edit_role_code">角色编码<span style="color:red;">*</span>:</label></td>
			<td><input type="text" id="roleTable_edit_role_code" name="role_code"></td>
		</tr>
		<tr>
			<td><label for="roleTable_edit_role_name">角色名称<span style="color:red;">*</span>:</label></td>
			<td><input type="text" id="roleTable_edit_role_name" name="role_name"></td>
		</tr>
		<tr>
			<td><label for="roleTable_edit_remark">备 注 :</label> </td>
			<td><input type="text" id="roleTable_edit_remark" name="remark"></td>
		</tr>
	</table>
	</form>
	
	<div id="authConfig" style="display:none">
		<div id="authList"></div>
	</div>
	<script type="text/javascript" src="${staticPath }/static/js/sys/role.js"></script>
</body>
</html>
