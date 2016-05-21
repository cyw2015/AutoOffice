$(document).ready(function() {
	var cmenu;//表头菜单
	//构造部门管理表格
	$("#deptTable").treegrid({
		url : 'person/getDeptPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		toolbar : '#deptTable_tool',
		idField: 'dept_code',
        treeField: 'dept_name',
        animate:true,
        collapsible:true,
        selectOnCheck: false,
        onBeforeSelect: function(){
			return false;
		},
		columns : [ [ {
			field : 'id',
			title : '编号',
			checkbox : true,
			width : 100
		}, {
			field : 'dept_name',
			title : '名称',
			sortable:true,
			width : 100
		}, {
			field : 'dept_code',
			title : '编号',
			width : 100
		},{
			field : 'dept_desc',
			title : '描述',
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
					$('#deptTable').treegrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#deptTable').treegrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#deptTable').treegrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#deptTable').treegrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}
	//添加框
	$('#deptTable_add').show().dialog({
	    title: '添加部门',
	    width: '33%',
	    height: '55%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funAddDept
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#deptTable_add').dialog('close').form('reset');
			}
		} ]
	});
	
	//添加框
	$('#deptTable_edit').show().dialog({
	    title: '修改部门',
	    width: '33%',
	    height: '55%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funEditDept
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#deptTable_edit').dialog('close').form('reset');
			}
		} ]
	});
	
	deptTable_tool={
		add: function() {
			initAdd();
			$('#deptTable_add').dialog('open').form('reset');
		},
		edit:function(){
			var nodes = $('#deptTable').treegrid('getChecked');//获取选定的nodes
			if (nodes.length > 1) {
				$.messager.alert("警告操作!", "只能选择编辑一条数据！", 'warning');
			}else if(nodes.length==0){
				$.messager.alert("警告操作!", "至少勾选一条数据", 'warning');
			}else{
				initEdit();
				$("#deptTable_edit_dept_code").textbox("setValue",nodes[0].dept_code);
				$("#deptTable_edit_dept_name").textbox("setValue",nodes[0].dept_name);
				$("#deptTable_edit_dept_desc").textbox("setValue",nodes[0].dept_desc);
				if(nodes[0].super_dept!='0')
					$('#deptTable_edit_super_dept').combotree("setValue",nodes[0].super_dept);
				$('#deptTable_edit').dialog('open');
			}
		},
		remove:function(){
			var rows = $('#deptTable').treegrid('getChecked');
			if(rows.length>0){
					$.messager.confirm('确定操作', '将删除选中项以及其子项,确认要删除吗？', function(flag) {
					if (flag) {
						var ids = [];
						for (var i = 0; i < rows.length; i++) {
							ids.push(rows[i].dept_code);
						}
						$.ajax({
							type : 'POST',
							url : 'person/deleteDept.do',
							data : {
								ids : ids.join(',')
							},
							beforeSend : function() {
								$('#deptTable').treegrid('loading');
							},
							success : function(data) {
								if (data.error!="1") {
									$('#deptTable').treegrid('loaded');
									$('#deptTable').treegrid('load');
									$('#deptTable').treegrid('unselectAll');
									$.messager.show({
										title : '提示',
										msg :'账号删除成功'
									});
								} else {
									$('#deptTable').treegrid('loaded');
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

//初始化添加框
function initAdd(){
	$("#deptTable_add_dept_code").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入编号',
	    required: true
	})
	$("#deptTable_add_dept_name").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入名称',
	    required: true
	})
	$("#deptTable_add_dept_desc").textbox({
	  	width:400,
	 	height:60,
	 	multiline:true
	})
	$('#deptTable_add_super_dept').combotree({
		width:400,
		height:30,
		panelMaxHeight:100,
		url:'person/getDeptTree.do',
		lines : true,
		// 全部展开
		onLoadSuccess : function(node, data) {
			if (data) {
				$(data).each(function(index, value) {
					if (this.state == "closed") {
						var t = $('#deptTable_add_super_dept').combotree('tree');	
						var n = t.tree('expandAll');
					}
				})
			}
		}
	})
}
//添加部门提交
function funAddDept(){
	if ($('#deptTable_add').form('validate')) {
		//获取上级部门编号
		var superDept = $('#deptTable_add_super_dept').combotree('getValue');
		if(superDept==null||superDept==""){
			superDept="0";
		}
		$.ajax({
			url:'person/addDept.do',
			type : 'post',
			cache : false,
			data : {
				deptCode:$("#deptTable_add_dept_code").val(),
				deptName:$("#deptTable_add_dept_name").val(),
				superDept:superDept,
				deptDesc:$('#deptTable_add_dept_desc').val()
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
					$('#deptTable_add').dialog('close').form('reset');
					$('#deptTable').treegrid('reload');
				} else {
					$.messager.alert("新增失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
				}
			}
		})
	}
}

//修改表单
function initEdit(){
	$("#deptTable_edit_dept_code").textbox({
	    width:150,
	    height:30,
	    readonly:true
	})
	$("#deptTable_edit_dept_name").textbox({
	    width:150,
	    height:30,
	    prompt:'请输入名称',
	    required: true
	})
	$("#deptTable_edit_dept_desc").textbox({
	  	width:400,
	 	height:60,
		multiline:true
	})
	$('#deptTable_edit_super_dept').combotree({
		width:400,
		height:30,
		panelMaxHeight:100,
		url:'person/getDeptTree.do',
		lines : true,
		// 全部展开
		onLoadSuccess : function(node, data) {
			if (data) {
				$(data).each(function(index, value) {
					if (this.state == "closed") {
						var t = $('#deptTable_edit_super_dept').combotree('tree');	
						var n = t.tree('expandAll');
					}
				})
			}
		}
	})
}

//修改保存
function funEditDept(){
	if ($('#deptTable_edit').form('validate')) {
		//获取上级部门编号
		var superDept = $('#deptTable_edit_super_dept').combotree('getValue');
		if(superDept==null||superDept==""){
			superDept="0";
		}
		$.ajax({
			url:'person/updateDept.do',
			type : 'post',
			cache : false,
			data : {
				deptCode:$("#deptTable_edit_dept_code").val(),
				deptName:$("#deptTable_edit_dept_name").val(),
				superDept:superDept,
				deptDesc:$('#deptTable_edit_dept_desc').val()
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
					$('#deptTable_edit').dialog('close').form('reset');
					$('#deptTable').treegrid('reload');
				} else {
					$.messager.alert("修改失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
				}
			}
		})
	}
}
