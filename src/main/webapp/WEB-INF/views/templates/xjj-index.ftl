<#include "/templates/xjj-global.ftl">
<#-- 导航 -->
<#macro nav navs...>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				Home
			</li>
			<#list navs as nav>
			<li>${nav}</li>
			</#list>
		</ul>
	</div>
</#macro>


<#macro navList navs>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				Home
			</li>
			<#if navs?exists>
			<#list navs as nav>
			<li>${nav}</li>
			</#list>
			</#if>
		</ul>
	</div>
</#macro>

<#-- index内容-->
<#macro content id=''>
	<div class="dataTables_wrapper form-inline no-footer">
		<#nested>
		<div id="${id}xjjlist">
		</div>
	</div>
	<script>
	$(function() {
		//添加tooltip提示
		$("[data-rel=tooltip]").tooltip();
		
		//默认查第一页
		XJJ.query({id:"${id}"});
	});
	</script>
</#macro>

<#--
 横向排列form表单里的一项
 title 标题
-->
<#macro querygroup title='title' >
<div class="form-group">
    <div class="input-group">
        <div class="input-group-addon">${title}：</div>
		<#nested>
    </div>
</div>
</#macro>

<#--查询-->
<#macro query queryUrl="" id=''>
    <div class="row">
		<div class="col-xs-12" style="padding-left:0px;">
			<div class="dataTables_length" id="dynamic-table_length">
				<form id="${id}queryForm" action="${queryUrl}" method="POST">
					<#nested>
				</form>
			</div>
		</div>
	</div>
	
	<script>
    XJJ.initQuery({id:'${id}'});
    $(document).ready(function(){
	    document.onkeydown = function(e){ 
			var ev = document.all ? window.event : e; 
			if(ev.keyCode==13) { 
				XJJ.query({id:'${id}',url:'${queryUrl}'});
			} 
		} 
    });
    </script>
</#macro>



<#-- 输入页面 -->
<#macro input id='' url=''>
	<form id="${id}inputForm" name="${id}inputForm" class="form-horizontal" role="form" action="${url}">
		<#nested>
	</form>
	<script type="text/javascript">
        XJJ.initInput({id:'${id}'});
     </script>
</#macro>