<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>部门管理</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div id="deptTable_tool" style="padding:5px;">
   		<div style="margin-bottom:5px">
   		<sec:authorize access="hasRole('ROLE_RES_PER_DEPT_ADD')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="deptTable_tool.add();">增加部门</a>
   		</sec:authorize>
   		<sec:authorize access="hasRole('ROLE_RES_PER_DEPT_EDIT')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="deptTable_tool.edit();">修改部门</a>
   		</sec:authorize>
   		<sec:authorize access="hasRole('ROLE_RES_PER_DEPT_DELETE')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deptTable_tool.remove();">删除</a>
   		</sec:authorize>
   		</div>
   	</div>
	<table id="deptTable"></table>

	<!-- 添加 -->
	<form id="deptTable_add" style="display:none">
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 			<td>
	 			<p style="margin:10px 0px">编号<span style="color:red;">*</span>: <input type="text" id="deptTable_add_dept_code" name="dept_code"></p>
	 			</td>
	 			<td >
	 			<p style="margin:10px 0px">名称<span style="color:red;">*</span>: <input type="text" id="deptTable_add_dept_name"  name="dept_name" style="width:70%" /></p>		
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2">
	 			<p style="margin:10px 0px">上级部门选择: <input id="deptTable_add_super_dept"></p> 
	 			</td>
	 		</tr>
	 		<tr>
	 			<td  colspan="2">
	 			<p style="margin:10px 0px">描述: <input id="deptTable_add_dept_desc"></p> 
	 			</td>
	 		</tr>
	 	</table>
	</form>
	
	<!-- 修改 -->
	<form id="deptTable_edit" style="display:none">
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 			<td>
	 			<p style="margin:10px 0px">编号<span style="color:red;">*</span>: <input type="text" id="deptTable_edit_dept_code" name="dept_code"></p>
	 			</td>
	 			<td >
	 			<p style="margin:10px 0px">名称<span style="color:red;">*</span>: <input type="text" id="deptTable_edit_dept_name"  name="dept_name" style="width:70%" /></p>		
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2">
	 			<p style="margin:10px 0px">上级部门选择: <input id="deptTable_edit_super_dept"></p> 
	 			</td>
	 		</tr>
	 		<tr>
	 			<td  colspan="2">
	 			<p style="margin:10px 0px">描述: <input id="deptTable_edit_dept_desc"></p> 
	 			</td>
	 		</tr>
	 	</table>
	</form>
	<script type="text/javascript" src="${staticPath }/static/js/person/dept.js"></script>
</body>
</html>
