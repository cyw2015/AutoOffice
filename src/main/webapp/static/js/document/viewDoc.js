$(document).ready(function() {
	var cmenu;//表头菜单
	$("#viewDocTable").datagrid({
		url : 'document/getViewDocPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		toolbar : '#viewDocTable_tool',
		sortName : 'pubTime',
		sortOrder : 'desc',
		columns : [ [ {
			field : 'id',
			title : '编号',
			checkbox : true,
			width : 100
		}, {
			field : 'doc_code',
			title : '公文编号',
			sortable:true,
			width : 100
		}, {
			field : 'doc_title',
			title : '公文标题',
			sortable:true,
			width : 100
		}, {
			field : 'creater',
			title : '发布人编号',
			sortable:true,
			width : 100
		},{
			field : 'creater_name',
			title : '发布人',
			sortable:true,
			width : 100
		},{
			field : 'pubTime',
			title : '发布时间',
			sortable:true,
			width : 100
		}]],
		onHeaderContextMenu: function(e, field){//表头菜单
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
					$('#viewDocTable').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#viewDocTable').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#viewDocTable').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#viewDocTable').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}
	
	viewDocTable_tool={
		lookDoc:function(){
			var rows = $('#viewDocTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "只能查看一条公文!", 'warning');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "请先选择一条公文查看!", 'warning');
			}else{
				if(rows[0].state=="0"){
					$.messager.alert("警告操作!", "该公文并未提交审核!", 'warning');
				}else{
					$('#viewDocDetail').show().window({
						    width:'80%',
						    height:'80%',
						    modal:true,
						    href:'document/getDocDetail.do',
						    minimizable:false,
						    title:'公文详情',
						    queryParams:{
						    	docCode:rows[0].doc_code,
						    	type:"2"
						    }
					})
				}
			}
		}
	}
})