$(document).ready(function() {
	 var cmenu;
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
					$('#userTable').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#userTable').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#userTable').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#userTable').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}
	
	//增加框表单输入框
	$("#userTable_add_user_code").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入账号',
	    required: true
	})
	
	$("#userTable_add_user_name").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入昵称',
	    required: true
	})
	
	$('#userTable_add_enabled').switchbutton({
		 	width:120,
		 	height:30,
            checked: true,
            onText:'启用',
            offText:'禁用',
            onChange: function(checked){
                if(checked){
                	$('#userTable_add_enabled_txt').val(1);
                }else {
                	$('#userTable_add_enabled_txt').val(0);
                }
            }
        })
        
    $('#userTable_add_issys').switchbutton({
		 	width:120,
		 	height:30,
            checked: true,
            onText:'是',
            offText:'否',
            onChange: function(checked){
                if(checked){
                	$('#userTable_add_issys_txt').val(1);
                }else {
                	$('#userTable_add_issys_txt').val(0);
                }
            }
        })
        
	$('#userTable_add_deadline').datebox({
    	width:120,
		height:30,
		editable:false
//		validType:'date'
	}).datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
//			var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate()+10);
			return d1<=date ;
		}
	});
	
	$("#userTable_add_remark").textbox({
			width:350,
		 	height:80
	});
	
	//增加对话框
	$('#userTable_add').show().dialog({
	    title: '添加用户',
	    width: '33%',
	    height: '60%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler : function(){
				if ($('#userTable_add').form('validate')) {
					$.ajax({
						url:'sys/addUser.do',
						type : 'post',
						cache : false,
						data : {
							userCode:$("#userTable_add_user_code").val(),
							userName:$("#userTable_add_user_name").val(),
							enabled:$("#userTable_add_enabled_txt").val(),
							isSys:$('#userTable_add_issys_txt').val(),
							deadLine:$('#userTable_add_deadline').datebox('getValue'),
							remark:$('#userTable_add_remark').val()
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
									msg : '新增用户信息成功'
								});
								$('#userTable_add').dialog('close').form('reset');
								$('#userTable').datagrid('reload');
							} else {
								$.messager.alert("新增失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
							}
						}
					})
				}
			}
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#userTable_add').dialog('close').form('reset');
			}
		} ]
	});
	
	
	//修改框表单输入框
	$("#userTable_edit_user_code").textbox({
	    width:150,
	    height:30,
	    readonly:true
	})
	
	$("#userTable_edit_user_name").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入昵称',
	    required: true
	})
	
	$('#userTable_edit_enabled').switchbutton({
		 	width:120,
		 	height:30,
            checked: true,
            onText:'启用',
            offText:'禁用',
            onChange: function(checked){
                if(checked){
                	$('#userTable_edit_enabled_txt').val(1);
                }else {
                	$('#userTable_edit_enabled_txt').val(0);
                }
            }
        })
        
    $('#userTable_edit_issys').switchbutton({
		 	width:120,
		 	height:30,
            checked: true,
            onText:'是',
            offText:'否',
            onChange: function(checked){
                if(checked){
                	$('#userTable_edit_issys_txt').val(1);
                }else {
                	$('#userTable_edit_issys_txt').val(0);
                }
            }
        })
        
	$('#userTable_edit_deadline').datebox({
    	width:120,
		height:30,
		editable:false
	}).datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1<=date ;
		}
	});
	
	$("#userTable_edit_remark").textbox({
			width:350,
		 	height:80
	});
	
	// 修改
	$('#userTable_edit').show().dialog({
	    title: '修改用户',
	    width: '33%',
	    height: '60%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler : function(){
				if ($('#userTable_edit').form('validate')) {
					$.ajax({
						url:'sys/updateUser.do',
						type : 'post',
						cache : false,
						data : {
							userCode:$("#userTable_edit_user_code").val(),
							userName:$("#userTable_edit_user_name").val(),
							enabled:$("#userTable_edit_enabled_txt").val(),
							isSys:$('#userTable_edit_issys_txt').val(),
							deadLine:$('#userTable_edit_deadline').datebox('getValue'),
							remark:$('#userTable_edit_remark').val()
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
									msg : '修改用户信息成功'
								});
								$('#userTable_edit').dialog('close').form('reset');
								$('#userTable').datagrid('reload');
							} else {
								$.messager.alert("修改失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
							}
						}
					})
				}
			}
		}, {
			text : '重置密码',
			iconCls : 'icon-reload',
			handler : function() {
				$.messager.confirm("重置确认",'您确定要重置该用户的密码吗?',function(r){
					if(r){
						$.ajax({
							url:'sys/resetPassword.do',
							type : 'post',
							cache : false,
							data : {
									userCode:$("#userTable_edit_user_code").val()
							},
							beforeSend : function() {
								$.messager.progress({
									text : '正在重置密码中...'
								});
							},
							success : function(data, response, status) {
								$.messager.progress('close');
								if (data.error!='1') {
									$.messager.show({
										title : '提示',
										msg : '重置密码成功',
										style:{
											right:'',
											top:document.body.scrollTop+document.documentElement.scrollTop,
											bottom:''
										}
									});
								} else {
									$.messager.alert("重置失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
								}
							}
					   })
					}	
				})
			}
			
		} , {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#userTable_edit').dialog('close').form('reset');
			}
		} ]
	});
	
	userTable_tool={
		add: function() {
			$('#userTable_add').dialog('open').form('reset');
		},
		edit:function(){
			var rows = $('#userTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "编辑记录只能选择一条数据！", 'warning');
			} else if (rows.length == 1) {
				$('#userTable_edit').form('load', {
					
				});
				$("#userTable_edit_user_code").textbox("setValue",rows[0].user_code);
				$("#userTable_edit_user_name").textbox("setValue",rows[0].user_name);
				if(rows[0].enabled=="1"){
					$("#userTable_edit_enabled").switchbutton("check");
				}else{
					$("#userTable_edit_enabled").switchbutton("uncheck");
				}
				$("#userTable_edit_enabled_txt").val(rows[0].enabled);
				if(rows[0].issys=="1"){
					$("#userTable_edit_issys").switchbutton("check");
				}else{
					$("#userTable_edit_issys").switchbutton("uncheck");
				}
				$('#userTable_edit_issys_txt').val(rows[0].issys);
				$('#userTable_edit_deadline').datebox('setValue',rows[0].deadline);
				$('#userTable_edit_remark').textbox("setValue",rows[0].remark);
				
				$('#userTable_edit').dialog('open');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "编辑记录至少选择一条", 'warning');
			}
		},
		remove:function(){
			var rows = $('#userTable').datagrid('getSelections');
			if(rows.length>0){
				$.messager.confirm('确定操作', '请谨慎删除,确认要删除吗？', function(flag) {
					if (flag) {
						var ids = [];
						for (var i = 0; i < rows.length; i++) {
							ids.push(rows[i].user_code);
						}
						$.ajax({
							type : 'POST',
							url : 'sys/deleteUsers.do',
							data : {
								ids : ids.join(',')
							},
							beforeSend : function() {
								$('#userTable').datagrid('loading');
							},
							success : function(data) {
								if (data.error!="1") {
									$('#userTable').datagrid('loaded');
									$('#userTable').datagrid('load');
									$('#userTable').datagrid('unselectAll');
									$.messager.show({
										title : '提示',
										msg :'账号删除成功'
									});
								} else {
									$('#userTable').datagrid('loaded');
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
			var rows = $('#userTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "只能选择一条数据！", 'warning');
			} else if (rows.length == 1) {
				var checkRows = [];
				$('#roleList').datalist({
			        url: 'sys/getConfigRole.do',
			        checkbox: true,
			        lines: true,
			        fit:true,
			        queryParams:{
			        	userCode:rows[0].user_code
			        },
			        valueField:'roleCode',
			        textField:'roleName',
					selectOnCheck: false,
					onBeforeSelect: function(){
						return false;
					},
					textFormatter:function(value,row,index){
						if(row.check){
							checkRows.push(index);
						}
						return '('+row.roleCode+')'+row.roleName;
					},
					onLoadSuccess:function(){
						 for(var i = 0;i<checkRows.length;i++){
			    			$('#roleList').datalist('checkRow',checkRows[i]);
			    		}
					}
			    });
			   
				//配置角色
				$('#roleConfig').show().dialog({
				    title: '配置角色',
				    width: '20%',
				    height: '40%',
				    closed: true,
				    cache: false,
				    modal: true,
				    border:'thin',cls:'c6',
				    buttons : [ {
						text : '提交',
						iconCls : 'icon-save',
						handler :funConfigRole
					}, {
						text : '取消',
						iconCls : 'icon-redo',
						handler : function() {
							$('#roleConfig').dialog('close');
						}
					} ]
				});
				$('#roleConfig').dialog('open');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "至少选择一条", 'warning');
			}
		
		}
	}
});

//配置角色提交
function funConfigRole(){
	var userCode = $('#userTable').datagrid('getSelections')[0].user_code;
	var rows = $('#roleList').datalist('getChecked');
	var roleCodes = [];
	for(var i = 0;i<rows.length;i++){
		roleCodes.push(rows[i].roleCode);
	}
	$.ajax({
		url:'sys/configRoleSave.do',
		type : 'post',
		cache : false,
		data : {
			userCode:userCode,
			roleCodes:roleCodes.join(',')
		},
		beforeSend : function() {
			$.messager.progress({
				text : '正在赋予角色中...'
			});
		},
		success : function(data, response, status) {
			$.messager.progress('close');
			if (data.error!='1') {
				$.messager.show({
					title : '提示',
					msg : '赋予角色成功'
				});
				$('#roleConfig').dialog('close');
				$('#userTable').datagrid('reload');
			} else {
				$.messager.alert("赋予角色失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
			}
		}
	})
}
