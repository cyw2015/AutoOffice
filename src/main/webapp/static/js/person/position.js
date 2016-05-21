$(document).ready(function() {
	var cmenu;//表头菜单
	//构造部门管理表格
	$("#posiTable").datagrid({
		url : 'person/getPosiPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		toolbar : '#posiTable_tool',
		sortName : 'posi_level',
		sortOrder : 'asc',
		columns : [ [ {
			field : 'id',
			title : '编号',
			checkbox : true,
			width : 100
		}, {
			field : 'posi_name',
			title : '名称',
			sortable:true,
			width : 100
		}, {
			field : 'posi_desc',
			title : '描述',
			width : 100
		},{
			field : 'posi_level',
			title : '级别',
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
					$('#posiTable').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#posiTable').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#posiTable').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#posiTable').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}
	
	
	//初始化
	$("#posiTable_add_posi_name").textbox({
	    width:200,
	    height:30,
	    prompt:'请输入名称',
	    required: true
	})
	$("#posiTable_add_posi_level").numberspinner({
	    width:200,
	    height:30,
		min: 0,
		precision:0,
	    prompt:'请输入等级',
	    required: true
	})
	$("#posiTable_add_posi_desc").textbox({
	  	width:200,
	 	height:60
	})
	//添加框
	$('#posiTable_add').show().dialog({
	    title: '添加岗位',
	    width: '20%',
	    height: '50%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funAddPosi
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#posiTable_add').dialog('close').form('reset');
			}
		} ]
	});
	
	//初始化修改
	$("#posiTable_edit_posi_name").textbox({
	    width:200,
	    height:30,
	    prompt:'请输入名称',
	    required: true
	})
	$("#posiTable_edit_posi_level").numberspinner({
	    width:200,
	    height:30,
		min: 0,
		precision:0,
	    prompt:'请输入等级',
	    required: true
	})
	$("#posiTable_edit_posi_desc").textbox({
	  	width:200,
	 	height:60
	})
	//添加框
	$('#posiTable_edit').show().dialog({
	    title: '修改岗位',
	    width: '20%',
	    height: '50%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funUpdatePosi
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#posiTable_edit').dialog('close').form('reset');
			}
		} ]
	});
	
	posiTable_tool={
		add: function() {
			$('#posiTable_add').dialog('open').form('reset');
		},
		edit:function(){
			var rows = $('#posiTable').datagrid('getSelections');//获取选定的rows
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "只能选择编辑一条数据！", 'warning');
			}else if(rows.length==0){
				$.messager.alert("警告操作!", "至少勾选一条数据", 'warning');
			}else{
				$('#posiTable_edit').form('load', {
					posi_name:rows[0].posi_name,
					posi_level:rows[0].posi_level,
					posi_desc:rows[0].posi_desc
				});
				$('#posiTable_edit').dialog('open');
			}
		},
		remove:function(){
			var rows = $('#posiTable').datagrid('getSelections');
			if(rows.length>0){
				$.messager.confirm('确定操作', '将删除选中项,确认要删除吗？', function(flag) {
					if (flag) {
						var ids = [];
						for (var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							type : 'POST',
							url : 'person/deletePosi.do',
							data : {
								ids : ids.join(',')
							},
							beforeSend : function() {
								$('#posiTable').datagrid('loading');
							},
							success : function(data) {
								if (data.error!="1") {
									$('#posiTable').datagrid('loaded');
									$('#posiTable').datagrid('load');
									$('#posiTable').datagrid('unselectAll');
									$.messager.show({
										title : '提示',
										msg :'删除成功'
									});
								} else {
									$('#posiTable').datagrid('loaded');
									$.messager.alert("警告操作!", data.errorMsg, 'warning');
								}
							}
						});
					
					}
				});
			}else {
				$.messager.alert('提示', '请选择要删除的记录!', 'info');
			}
		}
	}
})

function funAddPosi(){
	if ($('#posiTable_add').form('validate')) {
		$.ajax({
			url:'person/addPosi.do',
			type : 'post',
			cache : false,
			data : {
				posiName:$("#posiTable_add_posi_name").val(),
				posiLevel:$("#posiTable_add_posi_level").val(),
				posiDesc:$('#posiTable_add_posi_desc').val()
			},
			beforeSend : function() {
				$.messager.progress({
					text : '正在新增中...'
				});
			},
			success : function(data, response, status) {
				$.messager.progress('close');
				if (data.error!='1') {
					$.messager.show({
						title : '提示',
						msg : '新增成功'
					});
					$('#posiTable_add').dialog('close').form('reset');
					$('#posiTable').datagrid('reload');
				} else {
					$.messager.alert("新增失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
				}
			}
		})
	}
}


function funUpdatePosi(){
	if ($('#posiTable_edit').form('validate')) {
		//获取旧职位 和id
		var rows = $('#posiTable').datagrid('getSelections');//获取选定的rows
		$.ajax({
			url:'person/updatePosi.do',
			type : 'post',
			cache : false,
			data : {
				id:rows[0].id,
				oldPosName:rows[0].posi_name,
				posiName:$("#posiTable_edit_posi_name").val(),
				posiLevel:$("#posiTable_edit_posi_level").val(),
				posiDesc:$('#posiTable_edit_posi_desc').val()
			},
			beforeSend : function() {
				$.messager.progress({
					text : '正在修改中...'
				});
			},
			success : function(data, response, status) {
				$.messager.progress('close');
				if (data.error!='1') {
					$.messager.show({
						title : '提示',
						msg : '修改成功'
					});
					$('#posiTable_edit').dialog('close').form('reset');
					$('#posiTable').datagrid('reload');
				} else {
					$.messager.alert("修改失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
				}
			}
		})
	}

}