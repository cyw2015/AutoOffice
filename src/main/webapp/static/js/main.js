$(document).ready(function() {
	$('#nav').tree({
		url : 'sys/ResTree.do',
		lines : true,
		// 全部展开
		onLoadSuccess : function(node, data) {
			if (data) {
				$(data).each(function(index, value) {
					if (this.state == "closed") {
						$('#nav').tree('expandAll');
					}
				})
			}
		},
		onClick:function(node){
			if(node.url){
				//判断是否已经打开
				if($('#tabs').tabs('exists',node.text)){
					$('#tabs').tabs('select',node.text);
				}else{
					$('#tabs').tabs('add',{
						title:node.text,
						iconCls:node.iconCls,
						closable:true,
						href:ctx+node.url+'.do'
					});
				}
			}
		}
	})
	$('#tabs').tabs({
		fit : true,
		border : false,
		onContextMenu:function(e, title,index){
			e.preventDefault();
			if(index>=0){
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				}).data("tabTitle", title);
			}
		}
	});
	//右键菜单
	$("#mm").menu({
		onClick : function (item) {
			closeTab(this, item.name);
		}
	});
	//删除Tabs
	function closeTab(menu,type){
		var allTabs=$('#tabs').tabs('tabs');
		var allTabtitle=[];
		$.each(allTabs,function(i,n){
			var opt = $(n).panel('options');
			if(opt.closable)
				allTabtitle.push(opt.title);
		});
		var curTabTitle = $(menu).data("tabTitle");
		var curTabIndex = $("#tabs").tabs("getTabIndex",$("#tabs").tabs("getTab",curTabTitle));
		switch(type){
			case "1"://关闭当前
				$("#tabs").tabs("close",curTabIndex);
				return false;
				break;
			case "2"://全部关闭
				for(var i = 0;i<allTabtitle.length;i++){
					$('#tabs').tabs('close',allTabtitle[i]);
				}
				break;
			case "3"://除此之外全部关闭
				for(var i = 0;i<allTabtitle.length;i++){
					if(curTabTitle!=allTabtitle[i])
						$('#tabs').tabs('close',allTabtitle[i]);
				}
				$('#tabs').tabs('select',curTabTitle);
				break;
			case "4"://当前侧面右边
				for(var i = curTabIndex+1;i<allTabtitle.length;i++){
					$('#tabs').tabs('close',allTabtitle[i]);
				}
				$('#tabs').tabs("select",curTabTitle);
				break;
			case "5"://当前侧面左边
				for(var i = 0 ;i<curTabIndex;i++){
					$('#tabs').tabs('close',allTabtitle[i]);
				}
				$('#tabs').tabs("select",curTabTitle);
				break;
			case "6"://刷新
			 var panel = $("#tabs").tabs("getTab",curTabTitle).panel("refresh");
			 break;			
		}	
	}
	
});