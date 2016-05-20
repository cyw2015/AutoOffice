$(document).ready(function() {
	var cmenu;
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
		sortName : 'id',
		sortOrder : 'asc',
		columns : [ [ {
			field : '_id',
			title : '自动编号',
			width : 100,
			checkbox : true,
			formatter:function(value,row,index){
				return row.id;
			}
		},{
			field : 'id',
			title : '编号',
			sortable:true,
			width : 50
		},{
			field : 'resourceCode',
			title : '资源编码',
			sortable:true,
			width : 120
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
			width : 100,
			formatter:function(value,row,index){
				if(value=='1'){
					return "菜单";
				}else{
					return "功能";
				}
			}
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
		}]],
		onHeaderContextMenu: function(e, field){
			e.preventDefault();
			if (!cmenu){
				createColumnMenu();
			}
			cmenu.menu('show', {
				left:e.pageX,
				top:e.pageY
			});
		}
	});
	
	//表头菜单
	function createColumnMenu(){
		cmenu = $('<div/>').appendTo('body');
		cmenu.menu({ 
			onClick: function(item){
				if (item.iconCls == 'icon-ok'){
					$('#resTable').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#resTable').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#resTable').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#resTable').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}
});