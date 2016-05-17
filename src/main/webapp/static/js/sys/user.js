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
	//增加对话框
	$('#userTable_add').show().dialog({
	    title: '添加用户',
	    width: '33%',
	    height: '40%',
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
							deadLine:$('#userTable_add_deadline').datebox('getValue')
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
	
	userTable_tool={
		add: function() {
			$('#userTable_add').dialog('open').form('reset');
		}
	}
});

