<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>员工管理</title>
<%@ include file="/commons/basejs.jsp"%>	
<style type="text/css">
	.dv-table td{
			border:0;
		}
	.dv-label{
		font-weight:bold;
		color:#15428B;
		width:100px;
	}
</style>
</head>
<body>
	<div id="empTable_tool" style="padding:5px;">
   		<div style="margin-bottom:5px">
   		<sec:authorize access="hasRole('ROLE_RES_PER_EMP_ADD')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="empTable_tool.add();">增加员工</a>
   		</sec:authorize>
   		<sec:authorize access="hasRole('ROLE_RES_PER_EMP_EDIT')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="empTable_tool.edit();">修改员工</a>
   		</sec:authorize>
   		<sec:authorize access="hasRole('ROLE_RES_PER_EMP_DELETE')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="empTable_tool.remove();">删除</a>
   		</sec:authorize>
   		</div>
   	</div>
	<table id="empTable"></table>
	
	<!-- 添加 -->
	<form id="empTable_add" style="display:none" enctype="multipart/form-data"  method="post" >
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 		 	<td rowspan="3" colspan="3" style="width:250px;height:250px">
	 		 		<img style="width:250px;height:250px" src="${staticPath }/imageEmp/empDefault.jpg" id="empTable_add_emp_image" >
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_add_emp_code">员工编号<span style="color:red">*</span> :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_add_emp_code" name="empCode">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_add_emp_name">员工姓名<span style="color:red">*</span> :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_add_emp_name" name="empName">
	 		 	</td>
	 		</tr>
	 		<tr>
				<td>
	 		 		<label for="empTable_add_dept_name">部门名称<span style="color:red">*</span> :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_add_dept_name" name="deptName">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_add_posi_name">岗位名称<span style="color:red">*</span> :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_add_posi_name" name="posiName">
	 		 	</td>
	 		</tr>
	 		<tr>
				<td>
	 		 		<label for="empTable_add_emp_sex">性别  :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_add_emp_sex" name="empSex">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_add_telphone">联系方式 :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_add_telphone" name="telphone">
	 		 	</td>
	 		</tr>
	 		<tr>
	 			<td colspan="3" >
					<input  name="empImagefile" id="empImagefile" type="file" accept="image/*"   style="width:250px;"/>
	 			</td>
				<td>
	 		 		<label for="empTable_add_birthday">出生年月  :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_add_birthday" name="birthday">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_add_entrytime">入职时间 :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_add_entrytime" name="entryTime">
	 		 	</td>
		 	</tr>
		 	<tr>
		 		<td>
	 		 		<label for="empTable_add_email">邮箱  :</label>
	 		 	</td>
	 		 	<td  colspan="2">
	 		 		 <input id="empTable_add_email" name="email">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_add_address">地址 :</label>
	 		 	</td>
	 		 	<td  colspan="3">
	 		 		 <input id="empTable_add_address" name="address">
	 		 	</td>
		 	</tr>
	 	</table>
	</form>
	
	<!-- 修改员工-->
	<form id="empTable_edit" style="display:none" enctype="multipart/form-data"  method="post" >
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 		 	<td rowspan="3" colspan="3" style="width:250px;height:250px">
	 		 		<img style="width:250px;height:250px" src="${staticPath }/imageEmp/empDefault.jpg" id="empTable_edit_emp_image" >
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_edit_emp_code">员工编号<span style="color:red">*</span> :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_edit_emp_code" name="empCode">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_edit_emp_name">员工姓名<span style="color:red">*</span> :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_edit_emp_name" name="empName">
	 		 	</td>
	 		</tr>
	 		<tr>
				<td>
	 		 		<label for="empTable_edit_dept_name">部门名称<span style="color:red">*</span> :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_edit_dept_name" name="deptName">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_edit_posi_name">岗位名称<span style="color:red">*</span> :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_edit_posi_name" name="posiName">
	 		 	</td>
	 		</tr>
	 		<tr>
				<td>
	 		 		<label for="empTable_edit_emp_sex">性别  :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_edit_emp_sex" name="empSex">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_edit_telphone">联系方式 :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_edit_telphone" name="telphone">
	 		 	</td>
	 		</tr>
	 		<tr>
	 			<td colspan="3" >
					<input  name="empImagefile_edit" id="empImagefile_edit" type="file" accept="image/*"   style="width:250px;"/>
	 			</td>
				<td>
	 		 		<label for="empTable_edit_birthday">出生年月  :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_edit_birthday" name="birthday">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_edit_entrytime">入职时间 :</label>
	 		 	</td>
	 		 	<td>
	 		 		 <input id="empTable_edit_entrytime" name="entryTime">
	 		 	</td>
		 	</tr>
		 	<tr>
		 		<td>
	 		 		<label for="empTable_edit_email">邮箱  :</label>
	 		 	</td>
	 		 	<td  colspan="2">
	 		 		 <input id="empTable_edit_email" name="email">
	 		 	</td>
	 		 	<td>
	 		 		<label for="empTable_edit_address">地址 :</label>
	 		 	</td>
	 		 	<td  colspan="3">
	 		 		 <input id="empTable_edit_address" name="address">
	 		 	</td>
		 	</tr>
	 	</table>
	</form>
	
	<script type="text/javascript" src="${staticPath }/static/js/easyui/expand/datagrid-detailview.js"></script>
	<script type="text/javascript" src="${staticPath }/static/js/person/employ.js"></script>
</body>
</html>
