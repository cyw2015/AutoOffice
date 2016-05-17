$(document).ready(function() {
	$('#resTable').datagrid({
		url : 'sys/getResPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		toolbar : '#resTable_tool',
		sortName : 'resourceCode',
		sortOrder : 'desc',
		columns : [ [ {
			field : 'id',
			title : '自动编号',
			width : 100,
			checkbox : true
		},{
			field : 'resourceCode',
			title : '资源编码',
			sortable:true,
			width : 100
		}, {
			field : 'resourceName',
			title : '资源名称',
			sortable:true,
			width : 100
		}, {
			field : 'resourceDesc',
			title : '资源描述',
			width : 100
		},{
			field : 'state',
			title : '状态',
			width : 100
		} ,{
			field : 'type',
			title : '类型',
			width : 100
		},{
			field : 'iconcls',
			title : '图标',
			width : 100
		},{
			field : 'url',
			title : '连接',
			width : 100
		},{
			field : 'parent',
			title : '父id',
			width : 100
		},{
			field : 'remark',
			title : '备注',
			width : 100
		}]]
	})
});