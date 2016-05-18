$(document).ready(function() {
	var cmenu;
	$('#authTable').datagrid({
		url : 'sys/getAuthPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		toolbar : '#authTable_tool',
		sortName : 'authority_code',
		sortOrder : 'desc',
		columns : [ [ {
			field : 'id',
			title : '自动编号',
			width : 100,
			checkbox : true
		},{
			field : 'authority_code',
			title : '权限编号',
			width : 100,
			sortable:true
		},{
			field : 'authority_name',
			title : '权限名称',
			width : 100,
			sortable:true
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
			field : 'createDate',
			title : '创建日期',
			width : 100
		},{
			field : 'remark',
			title : '备注',
			width : 100
		}] ],
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
	})
	
	//表头菜单
	function createColumnMenu(){
		cmenu = $('<div/>').appendTo('body');
		cmenu.menu({ 
			onClick: function(item){
				if (item.iconCls == 'icon-ok'){
					$('#authTable').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#authTable').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#authTable').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#authTable').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}
	
	//增加表单样式
	$("#authTable_add_auth_code").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入权限编码',
	    required: true
	})
	$("#authTable_add_auth_name").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入权限名称',
	    required: true
	})
	$("#authTable_add_remark").textbox({
			width:150,
		 	height:80
	});
	//增加对话框
	$('#authTable_add').show().dialog({
	    title: '添加角色',
	    width: '20%',
	    height: '40%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funAddauth
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#authTable_add').dialog('close').form('reset');
			}
		} ]
	});
	
	//修改 表单样式
	$("#authTable_edit_auth_code").textbox({
	    width:150,
	    height:30,
	    readonly: true
	})
	$("#authTable_edit_auth_name").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入权限名称',
	    required: true
	})
	$("#authTable_edit_remark").textbox({
			width:150,
		 	height:80
	});
	//增加对话框
	$('#authTable_edit').show().dialog({
	    title: '修改权限',
	    width: '20%',
	    height: '40%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funUpdateAuth
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#authTable_edit').dialog('close').form('reset');
			}
		} ]
	});
	
	authTable_tool={
		add: function() {
			$('#authTable_add').dialog('open').form('reset');
		},
		edit:function(){
			var rows = $('#authTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "编辑记录只能选择一条数据！", 'warning');
			} else if (rows.length == 1) {
				$('#authTable_edit').form('load', {
					authority_code:rows[0].authority_code,
					authority_name:rows[0].authority_name,
					remark:rows[0].remark
				});
				$('#authTable_edit').dialog('open');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "编辑记录至少选择一条", 'warning');
			}
		},
		remove:function(){
			var rows = $('#authTable').datagrid('getSelections');
			if(rows.length>0){
				$.messager.confirm('确定操作', '请谨慎删除,确认要删除吗？', function(flag) {
					if (flag) {
						var ids = [];
						for (var i = 0; i < rows.length; i++) {
							ids.push(rows[i].authority_code);
						}
						$.ajax({
							type : 'POST',
							url : 'sys/deleteAuths.do',
							data : {
								ids : ids.join(',')
							},
							beforeSend : function() {
								$('#authTable').datagrid('loading');
							},
							success : function(data) {
								if (data.error!="1") {
									$('#authTable').datagrid('loaded');
									$('#authTable').datagrid('load');
									$('#authTable').datagrid('unselectAll');
									$.messager.show({
										title : '提示',
										msg :'删除权限成功'
									});
								} else {
									$('#authTable').datagrid('loaded');
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
	
});

//增加提交
function funAddauth(){
	if ($('#authTable_add').form('validate')) {
		$.ajax({
			url:'sys/addAuth.do',
			type : 'post',
			cache : false,
			data : {
				authCode:$("#authTable_add_auth_code").val(),
				authName:$("#authTable_add_auth_name").val(),
				remark:$('#authTable_add_remark').val()
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
						msg : '新增权限信息成功'
					});
					$('#authTable_add').dialog('close').form('reset');
					$('#authTable').datagrid('reload');
				} else {
					$.messager.alert("权限新增失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
				}
			}
		})
	}	
}

//修改权限信息
function funUpdateAuth(){
	if ($('#authTable_edit').form('validate')) {
		$.ajax({
			url:'sys/updateAuth.do',
			type : 'post',
			cache : false,
			data : {
				authCode:$("#authTable_edit_auth_code").val(),
				authName:$("#authTable_edit_auth_name").val(),
				remark:$('#authTable_edit_remark').val()
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
						msg : '修改权限信息成功'
					});
					$('#authTable_edit').dialog('close').form('reset');
					$('#authTable').datagrid('reload');
				} else {
					$.messager.alert("修改失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
				}
			}
		})
	}	
}