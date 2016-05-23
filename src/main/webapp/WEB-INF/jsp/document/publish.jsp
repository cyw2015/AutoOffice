<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>公文发布</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div id="docPubTable_tool" style="padding:5px;">
   		<div style="margin-bottom:5px">
   		<sec:authorize access="hasRole('ROLE_RES_DOC_PUB_ADD')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="docPubTable_tool.add();">增加公文</a>
   		</sec:authorize>
   		<sec:authorize access="hasRole('ROLE_RES_DOC_PUB_EDIT')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="docPubTable_tool.edit();">修改公文</a>
   		</sec:authorize>
   		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="docPubTable_tool.approval();">送审</a>
   		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="docPubTable_tool.lookAppr();">查看审批详情</a>
   		<sec:authorize access="hasRole('ROLE_RES_DOC_PUB_DELETE')">
   			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="docPubTable_tool.remove();">删除公文</a>
   		</sec:authorize>
   		</div>
   	</div>
	<table id="docPubTable"></table>
	
	<form id="docPubTable_add" style="display:none"  enctype="multipart/form-data"  method="post" >
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_add_doc_code">公文编号<span style="color:red">*</span> :</label>
	 			</td>
	 			<td>
	 					 <input id="docPubTable_add_doc_code" name="docCode">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_add_doc_title">标题<span style="color:red">*</span> :</label>
	 			</td>
	 			<td>
	 					 <input id="docPubTable_add_doc_title" name="docTitle">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_add_recipients">收件人<span style="color:red">*</span> :</label>
	 			</td>
	 			<td>
	 					 <input id="docPubTable_add_recipients" name="recipients">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_add_doc_content">内容 :</label>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2">
	 					 <input id="docPubTable_add_doc_content" name="docContent">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_add_attachment">附件 :</label>
	 			</td>
	 			<td>
	 					 <input id="docPubTable_add_attachment" name="attachment">
	 			</td>
	 		</tr>
	 	</table>
	</form>
	
	<!-- 修改 -->
	<form id="docPubTable_edit" style="display:none"  enctype="multipart/form-data"  method="post" >
	 	<table style="align:center;height:100%;width:100%;padding:10px;">
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_edit_doc_code">公文编号<span style="color:red">*</span> :</label>
	 			</td>
	 			<td>
	 					 <input id="docPubTable_edit_doc_code" name="docCode">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_edit_doc_title">标题<span style="color:red">*</span> :</label>
	 			</td>
	 			<td>
	 					 <input id="docPubTable_edit_doc_title" name="docTitle">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_edit_recipients">收件人<span style="color:red">*</span> :</label>
	 			</td>
	 			<td>
	 					 <input id="docPubTable_edit_recipients" name="recipients">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_edit_doc_content">内容 :</label>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2">
	 					 <input id="docPubTable_edit_doc_content" name="docContent">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="center" style="width:20%">	
	 					<label for="docPubTable_edit_attachment">附件 :</label>
	 			</td>
	 			<td>
	 					 <input id="docPubTable_edit_attachment" name="attachment">
	 					  <input id="docPubTable_edit_oldAttachment" type="hidden" name="oldAttachmentPath">
	 			</td>
	 		</tr>
	 	</table>
	</form>
	
	<div id="docPubDetail" style="display:none" ></div>
  	<script type="text/javascript" src="${staticPath }/static/js/document/publish.js"></script>
</body>
</html>
