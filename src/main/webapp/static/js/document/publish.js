$(document).ready(function() {
	var cmenu;//表头菜单
	//构造部门管理表格
	$("#docPubTable").datagrid({
		url : 'document/getDocPubPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		toolbar : '#docPubTable_tool',
		sortName : 'editTime',
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
		},{
			field : 'doc_content',
			title : '公文内容',
			sortable:true,
			width : 100
		},{
			field : 'editTime',
			title : '创建时间',
			sortable:true,
			width : 100
		},{
			field : 'pubTime',
			title : '发布时间',
			sortable:true,
			width : 100
		},{
			field : 'recipientsCode',
			title : '收文人编号',
			hidden:true,
			width : 100
		},{
			field : 'recipients',
			title : '收文人',
			width : 100
		},{
			field : 'state',
			title : '状态',
			width : 100,
			sortable:true,
			formatter:function(value,row,index){
				if(value=='0'){
					return "未送审";
				}else if(value=='1'){
					return "待审批";
				}else if(value=='2'){
					return "审批通过";
				}else if(value=='-1'){
					return "审批未通过";
				}
			}
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
					$('#docPubTable').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#docPubTable').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#docPubTable').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#docPubTable').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}

		//添加框
	$('#docPubTable_add').show().dialog({
	    title: '添加公文',
	    width: '50%',
	    height: '70%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :funAddDocPub
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#docPubTable_add').dialog('close').form('reset');
			}
		} ]
	});
	
		//修改框
	$('#docPubTable_edit').show().dialog({
	    title: '修改公文',
	    width: '50%',
	    height: '70%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :updatePub
		},{
			text : '查看原始附件',
			iconCls : 'icon-print',
			handler :function(){
				var rows = $('#docPubTable').datagrid('getSelections');
				if(rows[0].attachmentPath!=undefined&&rows[0].attachmentPath!=""){
					window.open("document/downloadDoc.do?attachment="+rows[0].attachment+"&attachmentPath="+rows[0].attachmentPath);
				}else{
					$.messager.alert("提示!", "没有原始附件！", 'info');
				}
			}
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#docPubTable_edit').dialog('close').form('reset');
			}
		} ]
	});
	
	docPubTable_tool={
		add: function() {
			initAdd();
			$('#docPubTable_add').dialog('open').form('reset');
		},
		remove:function(){
			deleteDoc();
		},
		edit:function(){
			var rows = $('#docPubTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "编辑记录只能选择一条数据！", 'warning');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "编辑记录至少选择一条", 'warning');
			}else{
				if(rows[0].state=="0"){
					initPubUpdate();
					$("#docPubTable_edit_doc_code").textbox("setValue",rows[0].doc_code);
					$("#docPubTable_edit_doc_title").textbox("setValue",rows[0].doc_title);
					$('#docPubTable_edit_recipients').combotree("setValues",rows[0].recipientsCode);
					$('#docPubTable_edit_doc_content').textbox("setValue",rows[0].doc_content);
					if(rows[0].attachmentPath!=undefined&&rows[0].attachmentPath!="")
						$('#docPubTable_edit_oldAttachment').val(rows[0].attachmentPath);
					$('#docPubTable_edit').dialog('open');	
				}else{
					$.messager.alert("警告操作!", "只能编辑未送审公文", 'warning');
				}
			}
		},
		approval:function(){
			var rows = $('#docPubTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "一次只能送审一条公文!", 'warning');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "请先选择一条公文数据!", 'warning');
			}else{
				if(rows[0].state!='0'){
					$.messager.alert("警告操作!", "只能处理未送审公文", 'warning');
				}else{
					$.messager.confirm('提示！', '确定要送审公文:<b style="color:blue">'+rows[0].doc_title+'</b>吗？', function(flag) {
						if (flag) {
							$.ajax({
								type : 'POST',
								url : 'document/sendAppr.do',
								data : {
									docCode:rows[0].doc_code
								},
								beforeSend : function() {
									$('#docPubTable').datagrid('loading');
								},
								success : function(data) {
									if (data.error!="1") {
										$('#docPubTable').datagrid('loaded');
										$('#docPubTable').datagrid('load');
										$('#docPubTable').datagrid('unselectAll');
										$.messager.show({
											title : '提示',
											msg :'送审成功,请等待审批人员审批'
										});
									} else {
										$('#docPubTable').datagrid('loaded');
										$.messager.alert("警告操作!", data.errorMsg, 'warning');
									}
								}
							});
						}
					})
				}
			}
		},
		lookAppr:function(){
			var rows = $('#docPubTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "只能查看一条公文审批过程!", 'warning');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "请先选择一条公文查看!", 'warning');
			}else{
				if(rows[0].state=="0"){
					$.messager.alert("警告操作!", "该公文并未提交审核!", 'warning');
				}else{
					$('#docPubDetail').show().window({
						    width:'80%',
						    height:'80%',
						    modal:true,
						    href:'document/getDocDetail.do',
						    minimizable:false,
						    title:'审核详情',
						    queryParams:{
						    	docCode:rows[0].doc_code,
						    	type:"1"
						    }
					})
				}
			}
		}
	}
})

//初始化添加
function initAdd(){

	$("#docPubTable_add_doc_code").textbox({
	    width:'100%',
	    height:30,
	    prompt:'请输入公文编号',
	    required: true
	})
	
	$("#docPubTable_add_doc_title").textbox({
	    width:'100%',
	    height:30,
	    prompt:'请输入公文标题',
	    required: true
	})
	
	$('#docPubTable_add_recipients').combotree({
		width:'100%',
		height:30,
		panelMaxHeight:200,
		multiple:true,
	    required: true,
		url:'person/getEnterprise.do',
		lines : true,
		// 全部展开
		onLoadSuccess : function(node, data) {
			if (data) {
				$(data).each(function(index, value) {
					if (this.state == "closed") {
						var t = $('#docPubTable_add_recipients').combotree('tree');	
						var n = t.tree('expandAll');
					}
				})
			}
		}
	})
	
	$("#docPubTable_add_doc_content").textbox({
	    width:'100%',
	    height:190,
	    multiline:true
	})
	
	$('#docPubTable_add_attachment').filebox({
		width:'100%',
	    height:30,
        buttonText: '选择附件',
        buttonAlign: 'right'
    })
}
function funAddDocPub(){
		$('#docPubTable_add').form({
		url:'document/addDocPub.do',
		iframe:false,
		onSubmit:function(){
			$.messager.progress({
					text : '正在新增中...'
			});
			return $(this).form('enableValidation').form('validate');
		},
	    success:function(data){
		  $.messager.progress('close');
	      var data = eval('(' + data + ')');
	      if (data.error!='1') {
				$.messager.show({
					title : '提示',
					msg : '新增成功'
				});
				$('#docPubTable_add').dialog('close').form('reset');
				$('#docPubTable').datagrid('reload');
			} else {
				$.messager.alert("新增失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
			}
	    }
	});
	$('#docPubTable_add').form('submit');
}

//删除操作
function deleteDoc(){
	var rows = $('#docPubTable').datagrid('getSelections');
	if(rows.length>0){
		$.messager.confirm('确定操作', '请谨慎删除,确认要删除吗？', function(flag) {
			if (flag) {
				var ids = [];
				var attachmentPaths=[];
				for (var i = 0; i < rows.length; i++) {
					if(rows[i].state!="0"){
						$.messager.alert("警告操作!","已送审或发布的公文不可删除", 'warning');
						return;
					}
					ids.push(rows[i].doc_code);
					attachmentPaths.push(rows[i].attachmentPaths)
				}
				$.ajax({
					type : 'POST',
					url : 'document/deletePubDoc.do',
					data : {
						ids : ids.join(','),
						attachmentPaths:attachmentPaths.join(',')
					},
					beforeSend : function() {
						$('#docPubTable').datagrid('loading');
					},
					success : function(data) {
						if (data.error!="1") {
							$('#docPubTable').datagrid('loaded');
							$('#docPubTable').datagrid('load');
							$('#docPubTable').datagrid('unselectAll');
							$.messager.show({
								title : '提示',
								msg :'删除成功'
							});
						} else {
							$('#docPubTable').datagrid('loaded');
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

//
function initPubUpdate(){
	$("#docPubTable_edit_doc_code").textbox({
	    width:'100%',
	    height:30,
	    readonly:true
	})
	
	$("#docPubTable_edit_doc_title").textbox({
	    width:'100%',
	    height:30,
	    prompt:'请输入公文标题',
	    required: true
	})
	
	$('#docPubTable_edit_recipients').combotree({
		width:'100%',
		height:30,
		panelMaxHeight:200,
		multiple:true,
	    required: true,
		url:'person/getEnterprise.do',
		lines : true,
		// 全部展开
		onLoadSuccess : function(node, data) {
			if (data) {
				$(data).each(function(index, value) {
					if (this.state == "closed") {
						var t = $('#docPubTable_edit_recipients').combotree('tree');	
						var n = t.tree('expandAll');
					}
				})
			}
		}
	})
	
	$("#docPubTable_edit_doc_content").textbox({
	    width:'100%',
	    height:190,
	    multiline:true
	})
	
	$('#docPubTable_edit_attachment').filebox({
		width:'100%',
	    height:30,
        buttonText: '选择附件',
        buttonAlign: 'right'
    })

}

function updatePub(){
		$('#docPubTable_edit').form({
		url:'document/updateDocPub.do',
		iframe:false,
		onSubmit:function(){
			$.messager.progress({
					text : '正在修改中...'
			});
			return $(this).form('enableValidation').form('validate');
		},
	    success:function(data){
		  $.messager.progress('close');
	      var data = eval('(' + data + ')');
	      if (data.error!='1') {
				$.messager.show({
					title : '提示',
					msg : '修改成功'
				});
				$('#docPubTable_edit').dialog('close').form('reset');
				$('#docPubTable').datagrid('reload');
			} else {
				$.messager.alert("修改失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
			}
	    }
	});
	$('#docPubTable_edit').form('submit');
	
}