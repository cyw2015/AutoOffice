$(document).ready(function() {
	$('#authTable').datagrid({
		url : '',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		sortName : 'UserName',
		sortOrder : 'desc',
		columns : [ [ {
			field : 'id',
			title : '自动编号',
			width : 100,
			checkbox : true
		}, {
			field : 'UserName',
			title : '管理员',
			sortable:true,
			width : 100
		}, {
			field : 'Password',
			title : '密码',
			width : 100
		} ] ]
	})
});