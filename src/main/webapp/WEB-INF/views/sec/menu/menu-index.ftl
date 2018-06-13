<#--
/****************************************************
 * Description: 菜单的列表页面
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-12 zhanghejie Create File
**************************************************/
-->
<#include "/templates/xjj-index.ftl">
<@navList navs=navArr/>
<script type="text/javascript">
 function toggle(id,obj){
		$("."+id).toggle();
		var innerH = $("#a"+id).html();
		if(innerH.indexOf('glyphicon-plus') > 0){
			$("#a"+id).html('<span class="glyphicon glyphicon-minus"></span>');
		}else{
			$("#a"+id).html('<span class="glyphicon glyphicon-plus"></span>');
			// 子的全部收起来
			$("."+id+" a").each(function(){
		     var childInnerH = $(this).html();
		     if(childInnerH.indexOf('glyphicon-minus') > 0){
		     	 $(this).trigger("click");
		     }
		  });
		}
	}
</script>

<@content id=tabId>
	<form id="${tabId}queryForm" action="${base}/sec/menu/list" method="POST"></form>
	<@button type="info" icon="glyphicon glyphicon-plus" onclick="XJJ.add('${base}/sec/menu/0/input','添加根菜单','${tabId}');">添加根菜单</@button>
</@content>