<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>用户管理</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div id="userTable_tool" style="padding:5px;">
   		<div style="margin-bottom:5px">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="userTable_tool.add();">增加用户</a>
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="userTable_tool.edit();">修改用户</a>
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="userTable_tool.remove();">删除</a>
   		</div>
   	</div>
	<table id="userTable"></table>
	<!-- 新增 -->
	<form id="userTable_add" style="display:none">
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 			<td>
	 			<p style="margin:10px 0px">账号<span style="color:red;">*</span>: <input type="text" id="userTable_add_user_code" name="user_code"></p>
	 			</td>
	 			<td >
	 			<p style="margin:10px 0px">昵称<span style="color:red;">*</span>: <input type="text" id="userTable_add_user_name"  name="user_name" style="width:70%" /></p>		
	 			</td>
	 		</tr>
	 		<tr>
	 			<td >
	 			<p style="margin:10px 0px">是否可用: <input id="userTable_add_enabled"> <input id="userTable_add_enabled_txt" style="display:none"></p> 
	 			</td>
	 			<td>
	 			<p style="margin:10px 0px"> 是否为系统用户: <input id="userTable_add_issys"><input id="userTable_add_issys_txt" style="display:none"></p>		
	 			</td>
	 		</tr>
	 		<tr>
	 			<td>
	 			<p style="margin:10px 0px">过期日期: <input id="userTable_add_deadline" type="text"></p> 
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2">
	 			<p style="margin:10px 0px">备注: <input id="userTable_add_remark" type="text"></p> 
	 			</td>
	 		</tr>
	 	</table>
	</form>
	
	<!-- 修改 -->
	<form id="userTable_edit" style="display:none">
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 			<td>
	 			<p style="margin:10px 0px">账号<span style="color:red;">*</span>: <input type="text" id="userTable_edit_user_code" name="user_code"></p>
	 			</td>
	 			<td >
	 			<p style="margin:10px 0px">昵称<span style="color:red;">*</span>: <input type="text" id="userTable_edit_user_name"  name="user_name" style="width:70%" /></p>		
	 			</td>
	 		</tr>
	 		<tr>
	 			<td >
	 			<p style="margin:10px 0px">是否可用: <input id="userTable_edit_enabled"> <input id="userTable_edit_enabled_txt" style="display:none"></p> 
	 			</td>
	 			<td>
	 			<p style="margin:10px 0px"> 是否为系统用户: <input id="userTable_edit_issys"><input id="userTable_edit_issys_txt" style="display:none"></p>		
	 			</td>
	 		</tr>
	 		<tr>
	 			<td>
	 			<p style="margin:10px 0px">过期日期: <input id="userTable_edit_deadline" type="text"></p> 
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2">
	 			<p style="margin:10px 0px">备注: <input id="userTable_edit_remark" type="text"></p>
	 			<input id="userTable_edit_password" type="text" style="display:none"> 
	 			</td>
	 		</tr>
	 	</table>
	</form>
	
	<script type="text/javascript" src="${staticPath }/static/js/sys/user.js"></script>
</body>
</html>
