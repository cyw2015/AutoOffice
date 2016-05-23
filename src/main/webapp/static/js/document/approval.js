$(document).ready(function() {
	var cmenu;//表头菜单
	//构造部门管理表格
	$("#docApprTable").datagrid({
		url : 'document/getApprovalPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pageNumber : 1,
		toolbar : '#docApprTable_tool',
		sortName : 'apprState',
		sortOrder : 'asc',
		columns : [ [ {
			field : 'id',
			title : '编号',
			checkbox : true,
			width : 100
		}, {
			field : 'docCode',
			title : '公文编号',
			sortable:true,
			width : 100
		}, {
			field : 'docTitle',
			title : '公文标题',
			width : 100
		},{
			field : 'editTime',
			title : '创建时间',
			sortable:true,
			width : 100
		},{
			field : 'creater',
			title : '创建人编号',
			sortable:true,
			hidden:true,
			width : 100
		},{
			field : 'createrName',
			title : '创建人',
			sortable:true,
			width : 100
		},{
			field : 'createrDept',
			title : '创建人部门',
			sortable:true,
			width : 100
		},{
			field : 'approver',
			title : '审批人编号',
			sortable:true,
			hidden:true, 
			width : 100
		},{
			field : 'approverName',
			title : '审批人',
			sortable:true,
			width : 100
		},{
			field : 'approverDept',
			title : '审批人部门',
			sortable:true,
			width : 100
		},{
			field : 'apprDate',
			title : '审批时间',
			sortable:true,
			width : 100
		},{
			field : 'apprAdvice',
			title : '审批意见',
			width : 100
		},{
			field : 'apprState',
			title : '状态',
			width : 100,
			sortable:true,
			formatter:function(value,row,index){
				if(value=='0'){
					return "未审批";
				}else if(value=='1'){
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
					$('#docApprTable').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#docApprTable').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
				}
			}
		});
		var fields = $('#docApprTable').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $('#docApprTable').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
		}
	}
	
	$("#docApprTable_edit_doc_code").textbox({
	    width:300,
	    height:30,
	    readonly: true
	})
	$("#docApprTable_edit_doc_title").textbox({
	    width: 300,
	    height:30,
	    readonly: true
	})

	$('#docApprTable_edit_is_pass').switchbutton({
		 	width:'50%',
		 	height:30,
            checked: true,
            onText:'通过',
            offText:'不通过',
            onChange: function(checked){
                if(checked){
                	$('#docApprTable_edit_is_pass_text').val(1);
                }else {
                	$('#docApprTable_edit_is_pass_text').val(0);
                }
			}
	})
	$("#docApprTable_edit_appr_advice").textbox({
	    width:'100%',
	    height:190,
	    multiline:true
	})
	
		//审批公文
	$('#docApprTable_edit').show().dialog({
	    title: '审批公文',
	    width: 400,
	    height: '70%',
	    closed: true,
	    cache: false,
	    modal: true,
	    border:'thin',cls:'c6',
	    buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			handler :editDocAppr
		}, {
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#docApprTable_edit').dialog('close').form('reset');
			}
		} ]
	});
	
	docApprTable_tool={
		edit: function() {
			var rows = $('#docApprTable').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert("警告操作!", "一次只能审批一条！", 'warning');
			} else if (rows.length == 0) {
				$.messager.alert("警告操作!", "请勾选要审批的公文", 'warning');
			}else{
				if(rows[0].apprState!='0'){
					$.messager.alert("警告操作!", "只能审批未审批公文", 'warning');
				}else{
					$('#docApprTable_edit').dialog().form('reset');
					$("#docApprTable_edit_doc_code").textbox("setValue",rows[0].docCode);
					$("#docApprTable_edit_doc_title").textbox("setValue",rows[0].docTitle);
					$('#docApprTable_edit').dialog('open');	
				}
			}
		}
	}
	
})
//审核提交
function editDocAppr(){
	$.ajax({
		url:'document/updateDocAppr.do',
		type : 'post',
		cache : false,
		data : {
			docCode:$("#docApprTable_edit_doc_code").val(),
			isPass:$("#docApprTable_edit_is_pass_text").val(),
			apprAdvice:$('#docApprTable_edit_appr_advice').textbox('getValue')
		},
		beforeSend : function() {
			$.messager.progress({
				text : '正在处理中...'
			});
		},
		success : function(data, response, status) {
			$.messager.progress('close');
			if (data.error!='1') {
				$.messager.show({
					title : '提示',
					msg : '审批成功'
				});
				$('#docApprTable_edit').dialog('close').form('reset');
				$('#docApprTable').datagrid('reload');
			} else {
				$.messager.alert("审批失败!", data.errorMsg!=undefined&&data.errorMsg!=null?data.errorMsg:"未知错误原因", 'warning');
			}
		}
	})	
}