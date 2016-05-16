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
		border : false
	});
});