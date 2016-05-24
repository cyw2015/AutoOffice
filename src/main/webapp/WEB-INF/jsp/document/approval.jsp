<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>公文审批</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div id="docApprTable_tool" style="padding:5px;">
   		<div style="margin-bottom:5px">
   		<sec:authorize access="hasRole('ROLE_RES_DOC_APPROVE_LOOK')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="docApprTable_tool.look();">查看详情</a>
   			</sec:authorize>
   			<sec:authorize access="hasRole('ROLE_RES_DOC_APPROVE_EDIT')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="docApprTable_tool.edit();">审批</a>
   			</sec:authorize>
   		</div>
   	</div>
	<table id="docApprTable"></table>
	
	<form id="docApprTable_edit" style="display:none"  >
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docApprTable_edit_doc_code">公文编号 :</label>
	 			</td>
	 			<td>
	 					 <input id="docApprTable_edit_doc_code" name="docCode">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docApprTable_edit_doc_title">公文标题:</label>
	 			</td>
	 			<td>
	 					 <input id="docApprTable_edit_doc_title" name="docTitle">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docApprTable_edit_is_pass">是否通过:</label>
	 			</td>
	 			<td>
	 					 <input id="docApprTable_edit_is_pass" >
	 					  <input id="docApprTable_edit_is_pass_text" type="hidden" name="state" value="1">
	 			</td>
	 		</tr>
	 		<tr>
				<td align="center" style="width:20%">	
	 					<label for="docApprTable_edit_appr_advice">审批意见 :</label>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2">
	 					 <input id="docApprTable_edit_appr_advice" name="apprAdvice">
	 			</td>
	 		</tr>
	 	</table>
	</form>
	
	<div id="apprDeatil" style="display:none" ></div>
  	<script type="text/javascript" src="${staticPath }/static/js/document/approval.js"></script>
</body>
</html>
