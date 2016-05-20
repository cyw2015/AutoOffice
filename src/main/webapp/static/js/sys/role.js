$(document).ready(function() {
	 var cmenu;
	$("#roleTable").datagrid({
		url : 'sys/getRolePage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		toolbar : '#roleTable_tool',
		sortName : 'role_code',
		sortOrder : 'desc',
		columns : [ [ {
			field : 'id',
			title : '自动编号',
			width : 100,
			checkbox : true
		}, {
			field : 'role_code',
			title : '角色编码',
			sortable:true,
			width : 100
		}, {
			field : 'role_name',
			title : '角色名称',
			sortable:true,
			width : 100
		}, {
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
		}, {
			field : 'createrDate',
			title : '创建时间',
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
	})
	
	//表格菜单
	function createColumnMenu(){
		cmenu = $('<div/>').appendTo('body');
		cmenu.menu({ 
			onClick: function(item){
				if (item.iconCls == 'icon-ok'){
					$('#roleTable').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#roleTable').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#roleTable').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#roleTable').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}
	
	
	//增加表单样式
	$("#roleTable_add_role_code").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入角色编码',
	    required: true
	})
	$("#roleTable_add_role_name").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入角色名称',
	    required: true
	})
	$("#roleTable_add_remark").textbox({
			width:150,
		 	height:80
	});
	//增加对话框
	$('#roleTable_add').show().dialog({
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
			handler :funAddRole
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#roleTable_add').dialog('close').form('reset');
			}
		} ]
	});
	
	//修改 表单样式
	$("#roleTable_edit_role_code").textbox({
	    width:150,
	    height:30,
	    readonly: true
	})
	$("#roleTable_edit_role_name").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入角色名称',
	    required: true
	})
	$("#roleTable_edit_remark").textbox({
			width:150,
		 	height:80
	});
	//增加对话框
	$('#roleTable_edit').show().dialog({
	    title: '修改角色',
	    width: '20%',
	    height: '40%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funUpdateRole
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#roleTable_edit').dialog('close').form('reset');
			}
		} ]
	});
	
	
	//表格工具栏菜单
	roleTable_tool={
		add: function() {
			$('#roleTable_add').dialog('open').form('reset');
		},
		edit:function(){
			var rows = $('#roleTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "编辑记录只能选择一条数据！", 'warning');
			} else if (rows.length == 1) {
				$('#roleTable_edit').form('load', {
					role_code:rows[0].role_code,
					role_name:rows[0].role_name,
					remark:rows[0].remark
				});
				$('#roleTable_edit').dialog('open');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "编辑记录至少选择一条", 'warning');
			}
		},
		remove:function(){
			var rows = $('#roleTable').datagrid('getSelections');
			if(rows.length>0){
				$.messager.confirm('确定操作', '请谨慎删除,确认要删除吗？', function(flag) {
					if (flag) {
						var ids = [];
						for (var i = 0; i < rows.length; i++) {
							ids.push(rows[i].role_code);
						}
						$.ajax({
							type : 'POST',
							url : 'sys/deleteRoles.do',
							data : {
								ids : ids.join(',')
							},
							beforeSend : function() {
								$('#roleTable').datagrid('loading');
							},
							success : function(data) {
								if (data.error!="1") {
									$('#roleTable').datagrid('loaded');
									$('#roleTable').datagrid('load');
									$('#roleTable').datagrid('unselectAll');
									$.messager.show({
										title : '提示',
										msg :'角色删除成功'
									});
								} else {
									$('#roleTable').datagrid('loaded');
									$.messager.alert("警告操作!", data.errorMsg, 'warning');
								}
							}
						});
					}
				});
			}else {
				$.messager.alert('提示', '请选择要删除的记录!', 'info');
			}
		},
		config:function(){
			var rows = $('#roleTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "只能选择一条数据！", 'warning');
			} else if (rows.length == 1) {
				var checkRows = [];
				$('#authList').datalist({
			        url: 'sys/getAllAuth.do',
			        checkbox: true,
			        lines: true,
			        queryParams:{
			        	roleCode:rows[0].role_code
			        },
			        fit:true,
			        valueField:'authCode',
			        textField:'authName',
					selectOnCheck: false,
					onBeforeSelect: function(){
						return false;
					},
					textFormatter:function(value,row,index){
						if(row.check){
							checkRows.push(index);
						}
						return '('+row.authCode+')'+row.authName;
					},
					onLoadSuccess:function(){
						 for(var i = 0;i<checkRows.length;i++){
			    			$('#authList').datalist('checkRow',checkRows[i]);
			    		}
					}
			    });
			   
				//配置权限
				$('#authConfig').show().dialog({
				    title: '配置权限',
				    width: '20%',
				    height: '40%',
				    closed: true,
				    cache: false,
				    modal: true,
				    border:'thin',cls:'c6',
				    buttons : [ {
						text : '提交',
						iconCls : 'icon-save',
						handler :funConfigAuth
					}, {
						text : '取消',
						iconCls : 'icon-redo',
						handler : function() {
							$('#authConfig').dialog('close');
						}
					} ]
				});
				$('#authConfig').dialog('open');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "至少选择一条", 'warning');
			}
		
		}
	}
})

//添加角色 提交
function funAddRole(){
	if ($('#roleTable_add').form('validate')) {
		$.ajax({
			url:'sys/addRole.do',
			type : 'post',
			cache : false,
			data : {
				roleCode:$("#roleTable_add_role_code").val(),
				roleName:$("#roleTable_add_role_name").val(),
				remark:$('#roleTable_add_remark').val()
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
						msg : '新增角色信息成功'
					});
					$('#roleTable_add').dialog('close').form('reset');
					$('#roleTable').datagrid('reload');
				} else {
					$.messager.alert("角色新增失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
				}
			}
		})
	}	
}
//修改角色提交
function funUpdateRole(){
	if ($('#roleTable_edit').form('validate')) {
		$.ajax({
			url:'sys/updateRole.do',
			type : 'post',
			cache : false,
			data : {
				roleCode:$("#roleTable_edit_role_code").val(),
				roleName:$("#roleTable_edit_role_name").val(),
				remark:$('#roleTable_edit_remark').val()
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
						msg : '修改角色信息成功'
					});
					$('#roleTable_edit').dialog('close').form('reset');
					$('#roleTable').datagrid('reload');
				} else {
					$.messager.alert("修改失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
				}
			}
		})
	}	
}

//配置权限提交
function funConfigAuth(){
	var roleCode = $('#roleTable').datagrid('getSelections')[0].role_code;
	var rows = $('#authList').datalist('getChecked');
	var authCodes = [];
	for(var i = 0;i<rows.length;i++){
		authCodes.push(rows[i].authCode);
	}
	$.ajax({
		url:'sys/configAuth.do',
		type : 'post',
		cache : false,
		data : {
			roleCode:roleCode,
			authCodes:authCodes.join(',')
		},
		beforeSend : function() {
			$.messager.progress({
				text : '正在配置中...'
			});
		},
		success : function(data, response, status) {
			$.messager.progress('close');
			if (data.error!='1') {
				$.messager.show({
					title : '提示',
					msg : '配置权限成功'
				});
				$('#authConfig').dialog('close');
				$('#roleTable').datagrid('reload');
			} else {
				$.messager.alert("配置权限失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
			}
		}
	})
}