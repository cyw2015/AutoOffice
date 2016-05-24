<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>公文详情页</title>
<%@ include file="/commons/basejs.jsp"%>
</head>
<body>
	<div style="background: #fff none repeat scroll 0 0;
				font-family: Arial,Helvetica,sans-serif;
			    font-size: 12px;
			    line-height: 25px;width:100%"> 
			<div style="border: 5px solid #e5f1ff;border-radius: 4px;padding:10px 20px">
				<h1  style="text-align:center;
				border-bottom: 1px dotted #b5b5b5;
			    color: #184aa2;
			    line-height: 20px;
			    padding: 20px;">${docTitle}</h1>
				<div style=" height: 30px;
				    line-height: 30px;
				    text-align: right;">
				    <span style="color: #999;">发布人:</span><a href="#" title="所属部门:${createrDeptName }" class="easyui-tooltip" style="color:#666">${createrName }</a>
				    <span style="color: #999;">发布时间:</span>${pubtime }
				</div>
				<div   style="padding: 25px;">
					${docContent }
					<br>
					<div>收件人:
						<c:forEach  var="r" items="${rec}" >
							<a href="#" title="所属部门:${r.recDeptName }" class="easyui-tooltip" style="color:#666">${r.recName }</a>
						</c:forEach></div>
					<div>附件:<a href="document/downloadDoc.do?attachmentPath=${attachmentPath }&attachment=${attachment }" style="color:blue">${attachment }</a></div>	
				</div>
				<c:if test="${type=='1' }">
				<div   style="padding: 25px;">
				<table style="width:100%;border-collapse: collapse;border: 1px solid #88b1f8;">
						<tr>
							<td style="width:15%; color: #184aa2;" align="center"  >
								审批结果:
							</td>
							<td style="width:85%">
								
								<c:if test="${appr.state=='0' }">
									<span style="color:gray">待审批</span>
								</c:if> 
								<c:if test="${appr.state=='1' }">
									<span style="color:blue">审批通过</span>
								</c:if> 
								<c:if test="${appr.state=='-1' }">
									<span style="color:red">审批未通过</span>
								</c:if> 
							</td>
						</tr>
						<tr>
							<td  align="center" style=" color: #184aa2;">
								审批时间:
							</td>
							<td>
								${appr.apprDate }
							</td>
						</tr>
						<tr>
							<td  align="center" style=" color: #184aa2;">
								审批内容:
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">${appr.apprAdvice }</td>
						</tr>
						<tr>
							<td  align="center"style=" color: #184aa2;">
								审批人:
							</td>
							<td >
								${appr.apprName }
							</td>
						</tr>
					</table>
				</div>
				</c:if>
			</div>
	</div>
</body>
</html>
