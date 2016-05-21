$(document).ready(function() {
	var cmenu;//表头菜单
	//构造部门管理表格
	$("#empTable").datagrid({
		url : 'person/getEmpPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		toolbar : '#empTable_tool',
		sortName : 'emp_code',
		sortOrder : 'asc',
		columns : [ [ {
			field : 'id',
			title : '编号',
			checkbox : true,
			width : 100
		}, {
			field : 'emp_code',
			title : '员工编号',
			sortable:true,
			width : 100
		}, {
			field : 'emp_name',
			title : '姓名',
			sortable:true,
			width : 100
		},{
			field : 'emp_sex',
			title : '性别',
		    sortable:true,
			width : 100,
			formatter:function(value,row,index){
				if(value=='1'){
					return "男";
				}else if(value=='2'){
					return "女";
				}
			}
		},{
			field : 'dept_code',
			title : '部门编号',
			sortable:true,
			hidden:true,
			width : 100
		},{
			field : 'dept_name',
			title : '部门名称',
			sortable:true,
			width : 100
		},{
			field : 'posi_code',
			title : '职位编号',
			hidden:true,
			sortable:true,
			width : 100
		},{
			field : 'posi_name',
			title : '职位名称',
			sortable:true,
			width : 100
		},{
			field : 'entrytime',
			title : '入职时间',
			sortable:true,
			width : 100
		},{
			field : 'birthday',
			title : '出生年月',
			sortable:true,
			width : 100
		},{
			field : 'telphone',
			title : '联系方式',
			width : 100
		},{
			field : 'email',
			title : '邮箱',
			hidden:true,
			width : 100
		},{
			field : 'address',
			hidden:true,
			title : '地址',
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
		},
		view: detailview,
	    detailFormatter: function(rowIndex, rowData){
	    	var image="empDefault.jpg";
	    	if(rowData.emp_image!=""){
	    		image=rowData.emp_image;
	    	}
	    	return '<table class="dv-table" border="0" style="width:100%;"><tr>'+
	    	'<td rowspan="3" style="width:60px;height:60px;">'+
	    	'<img src="imageEmp/'+image+'" style="width:60px;height:60px;" /></td>'+
	    	'<td class="dv-label">员工编号 :</td>'+
	    	'<td>'+rowData.emp_code+'</td>'+
	    	'<td class="dv-label">姓名 :</td>'+
	    	'<td>'+rowData.emp_name+'</td>'+
	    	'<td class="dv-label">部门编号 :</td>'+
	    	'<td>'+rowData.dept_code+'</td>'+
	    	'<td class="dv-label">部门名称 :</td>'+
	    	'<td>'+rowData.dept_name+'</td></tr>'+
	    	'<tr><td class="dv-label">性别 : </td>'+
	    	'<td>'+rowData.emp_sex+'</td>'+
	    	'<td class="dv-label">出生年月 : </td>'+
	    	'<td>'+rowData.birthday+'</td>'+
	    	'<td class="dv-label">职位编号 : </td>'+
	    	'<td>'+rowData.posi_code+'</td>'+
	    	'<td class="dv-label">职位名称 : </td>'+
	    	'<td>'+rowData.posi_name+'</td></tr>'+
	    	'<tr><td class="dv-label">联系方式 : </td>'+
	    	'<td>'+rowData.telphone+'</td>'+
	    	'<td class="dv-label">邮箱 : </td>'+
	    	'<td>'+rowData.email+'</td>'+
	    	'<td class="dv-label">地址 : </td>'+
	    	'<td colspan="3">'+rowData.address+'</td></tr></table>';
	    }
	});
	
	//表头菜单
	function createColumnMenu(){
		cmenu = $('<div/>').appendTo('body');
		cmenu.menu({ 
			onClick: function(item){
				if (item.iconCls == 'icon-ok'){
					$('#empTable').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#empTable').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#empTable').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#empTable').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}
	
	
	//添加框
	$('#empTable_add').show().dialog({
	    title: '添加员工',
	    width: '60%',
	    height: '70%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funAddEmp
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#empTable_add').dialog('close').form('reset');
			}
		} ]
	});
	
	//修改
	$('#empTable_edit').show().dialog({
	    title: '修改员工',
	    width: '60%',
	    height: '70%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funUpdateEmp
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#empTable_edit').dialog('close').form('reset');
			}
		} ]
	});
	empTable_tool={
		add: function() {
			initEmpAdd();
			$('#empTable_add').dialog('open').form('reset');
		},
		edit:function(){
			var rows = $('#empTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "编辑记录只能选择一条数据！", 'warning');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "编辑记录至少选择一条", 'warning');
			}else{
				initEmpUpdate();
				$("#empTable_edit_emp_code").textbox("setValue",rows[0].emp_code);
				$("#empTable_edit_emp_name").textbox("setValue",rows[0].emp_name);
				$('#empTable_edit_dept_name').combotree("setValue",rows[0].dept_code);
				$('#empTable_edit_posi_name').combobox("setValue",rows[0].posi_code);
				$('#empTable_edit_emp_sex').combobox("setValue",rows[0].emp_sex);
				$('#empTable_edit_telphone').numberbox("setValue",rows[0].telphone);
				$('#empTable_edit_birthday').datebox("setValue",rows[0].birthday);
				$('#empTable_edit_entrytime').datebox("setValue",rows[0].entrytime);
				$("#empTable_edit_email").textbox("setValue",rows[0].email);
				$("#empTable_edit_address").textbox("setValue",rows[0].address);
				if(rows[0].emp_image!=undefined&&rows[0].emp_image!=""){
					$("#empTable_edit_emp_image").attr("src", ctx+'/imageEmp/'+rows[0].emp_image) ;
				}
				$('#empTable_edit').dialog('open');
			}
		},
		remove:function(){
			var rows = $('#empTable').datagrid('getSelections');
			if(rows.length>0){
				$.messager.confirm('确定操作', '将删除选中项,确认要删除吗？', function(flag) {
					if (flag) {
						var ids = [];
						var empImages=[];
						for (var i = 0; i < rows.length; i++) {
							ids.push(rows[i].emp_code);
							empImages.push(rows[i].emp_image);
						}
						$.ajax({
							type : 'POST',
							url : 'person/deleteEmploy.do',
							data : {
								ids : ids.join(','),
								empImages:empImages.join(',')
							},
							beforeSend : function() {
								$('#empTable').datagrid('loading');
							},
							success : function(data) {
								if (data.error!="1") {
									$('#empTable').datagrid('loaded');
									$('#empTable').datagrid('load');
									$('#empTable').datagrid('unselectAll');
									$.messager.show({
										title : '提示',
										msg :'删除成功'
									});
								} else {
									$('#empTable').datagrid('loaded');
									$.messager.alert("警告操作!", data.errorMsg, 'warning');
								}
							}
						});
					}
				})
			}else {
				$.messager.alert('提示', '请选择要删除的记录!', 'info');
			}
		}
	}	
});

//建立一個可存取到該file的url
function getObjectURL(file) {
	 var url = null ; 
	 if (window.createObjectURL!=undefined) { // basic
	  url = window.createObjectURL(file) ;
	 } else if (window.URL!=undefined) { // mozilla(firefox)
	  url = window.URL.createObjectURL(file) ;
	 } else if (window.webkitURL!=undefined) { // webkit or chrome
	  url = window.webkitURL.createObjectURL(file) ;
	 }
	 return url ;
}

//初始化弹框
function initEmpAdd(){

	//图片
	$("#empImagefile").change(function(){
	 	var objUrl = getObjectURL(this.files[0]) ;
		 if (objUrl) {
		  $("#empTable_add_emp_image").attr("src", objUrl) ;
		 }
	}) ;
	$("#empTable_add_emp_code").textbox({
	    width:200,
	    height:30,
	    prompt:'请输入员工编号',
	    required: true
	})
	$("#empTable_add_emp_name").textbox({
	    width:200,
	    height:30,
	    prompt:'请输入员工姓名',
	    required: true
	})
	$('#empTable_add_dept_name').combotree({
		width:200,
		height:30,
		panelMaxHeight:100,
		url:'person/getDeptTree.do',
		required: true,
		lines : true,
		// 全部展开
		onLoadSuccess : function(node, data) {
			if (data) {
				$(data).each(function(index, value) {
					if (this.state == "closed") {
						var t = $('#empTable_add_dept_name').combotree('tree');	
						var n = t.tree('expandAll');
					}
				})
			}
		}
	});
	$('#empTable_add_posi_name').combobox({
		width:200,
		height:30,
		panelMaxHeight:100,
		required: true,
		editable:false,
        url:'person/getComboPosi.do',
        valueField:'id',
        textField:'posi_name'
    });
    $('#empTable_add_emp_sex').combobox({
		width:200,
		height:30,
        valueField:'id',
        textField:'text',
        panelMaxHeight:50,
        required: true,
        editable:false,
        data:[{
        	id:'1',
        	text:'男'
        },{
        	id:'2',
        	text:'女'
        }]
    });
    
    $("#empTable_add_telphone").numberbox({
	    width:200,
	    height:30,
	    validType:'length[11,20]'
	});
	
	$('#empTable_add_birthday').datebox({
    	 width:200,
	    height:30,
		editable:false
	}).datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1>=date ;
		}
	});
	
	$('#empTable_add_entrytime').datebox({
    	 width:200,
	    height:30,
		editable:false
	})
	
	$("#empTable_add_email").textbox({
	    width:200,
	    height:30,
	    validType:'email'
	})
	
	$("#empTable_add_address").textbox({
	    width:450,
	    height:30,
	    multiline:true
	})
}

function funAddEmp(){
	$('#empTable_add').form({
		url:'person/addEmploy.do',
		iframe:false,
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		},
	    success:function(data){
	      var data = eval('(' + data + ')');
	      if (data.error!='1') {
				$.messager.show({
					title : '提示',
					msg : '新增成功'
				});
				$('#empTable_add').dialog('close').form('reset');
				$('#empTable').datagrid('reload');
			} else {
				$.messager.alert("新增失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
			}
	    }
	});
	$('#empTable_add').form('submit');
}

function initEmpUpdate(){

	//图片
	$("#empImagefile_edit").change(function(){
	 	var objUrl = getObjectURL(this.files[0]) ;
		 if (objUrl) {
		  $("#empTable_edit_emp_image").attr("src", objUrl) ;
		 }
	}) ;
	$("#empTable_edit_emp_code").textbox({
	    width:200,
	    height:30,
	    readonly: true
	})
	$("#empTable_edit_emp_name").textbox({
	    width:200,
	    height:30,
	    prompt:'请输入员工姓名',
	    required: true
	})
	$('#empTable_edit_dept_name').combotree({
		width:200,
		height:30,
		panelMaxHeight:100,
		url:'person/getDeptTree.do',
		required: true,
		lines : true,
		// 全部展开
		onLoadSuccess : function(node, data) {
			if (data) {
				$(data).each(function(index, value) {
					if (this.state == "closed") {
						var t = $('#empTable_edit_dept_name').combotree('tree');	
						var n = t.tree('expandAll');
					}
				})
			}
		}
	});
	$('#empTable_edit_posi_name').combobox({
		width:200,
		height:30,
		panelMaxHeight:100,
		required: true,
		editable:false,
        url:'person/getComboPosi.do',
        valueField:'id',
        textField:'posi_name'
    });
    $('#empTable_edit_emp_sex').combobox({
		width:200,
		height:30,
        valueField:'id',
        textField:'text',
        panelMaxHeight:50,
        required: true,
        editable:false,
        data:[{
        	id:'1',
        	text:'男'
        },{
        	id:'2',
        	text:'女'
        }]
    });
    
    $("#empTable_edit_telphone").numberbox({
	    width:200,
	    height:30,
	    validType:'length[11,20]'
	});
	
	$('#empTable_edit_birthday').datebox({
    	 width:200,
	    height:30,
		editable:false
	}).datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1>=date ;
		}
	});
	
	$('#empTable_edit_entrytime').datebox({
    	 width:200,
	    height:30,
		editable:false
	})
	
	$("#empTable_edit_email").textbox({
	    width:200,
	    height:30,
	    validType:'email'
	})
	
	$("#empTable_edit_address").textbox({
	    width:450,
	    height:30,
	    multiline:true
	})	
}

//修改提交
function funUpdateEmp(){
	$('#empTable_edit').form({
		url:'person/updateEmploy.do',
		iframe:false,
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		},
	    success:function(data){
	      var data = eval('(' + data + ')');
	      if (data.error!='1') {
				$.messager.show({
					title : '提示',
					msg : '修改成功'
				});
				$('#empTable_edit').dialog('close').form('reset');
				$('#empTable').datagrid('reload');
			} else {
				$.messager.alert("修改失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
			}
	    }
	});
	$('#empTable_edit').form('submit');
}