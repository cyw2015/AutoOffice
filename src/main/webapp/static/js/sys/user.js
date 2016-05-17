$(document).ready(function() {
	$("#userTable").datagrid({
		url : 'sys/getUserPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		toolbar : '#userTable_tool',
		sortName : 'user_code',
		sortOrder : 'desc',
		columns : [ [ {
			field : 'id',
			title : '自动编号',
			width : 100,
			checkbox : true
		},{
			field : 'user_code',
			title : '账号',
			sortable:true,
			width : 100
		}, {
			field : 'user_name',
			title : '昵称',
			width : 100
		}, {
			field : 'user_password',
			title : '密码',
			width : 100,
			formatter:function(value,row,index){
				return "******";
			}
		},{
			field : 'enabled',
			title : '是否可用',
			width : 100,
			formatter:function(value,row,index){
				if(value=='1'){
					return "是";
				}else {
					return "否";
				}
			}
		} ,{
			field : 'issys',
			title : '是否为系统用户',
			width : 100,
			formatter:function(value,row,index){
				if(value=='1'){
					return "是";
				}else{
					return "否";
				}
			}
		},{
			field : 'creater',
			title : '创建人',
			width : 100,
			formatter:function(value,row,index){
				if(value=='-1'){
					return "";
				}else{
					return value;
				}
			}
		},{
			field : 'createdate',
			title : '创建时间',
			width : 100
		},{
			field : 'deadline',
			title : '过期时间',
			width : 100
		},{
			field : 'last_login',
			title : '上一次登录',
			width : 100
		},{
			field : 'login_count',
			title : '登录次数',
			width : 100
		},{
			field : 'remark',
			title : '备注',
			width : 100
		}]]
	})
});