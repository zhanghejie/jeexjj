<#include "/templates/xjj-index.ftl">
<#--导航-->
<@navList navs=navArr/>

<@content id=tabId>
	<#--查询区-->
	<@query queryUrl="${base}/sec/manager/list" id=tabId>
	
		<@querygroup title='姓名'>
			<input type="search" name="query.userName@lk@s" class="form-control input-sm" placeholder="请输入姓名" aria-controls="dynamic-table">
	    </@querygroup>
	    
		<@querygroup title='手机'>
			<input type="search" name="query.mobile@eq@s" class="form-control input-sm" placeholder="请输入手机号" aria-controls="dynamic-table">
	    </@querygroup>
	    
	    
		<@querygroup title='状态'>
			<@select name="query.status@eq@s" list=XJJConstants.COMMON_STATUS_LIST></@select>
	    </@querygroup>
	    
		<@button type="info" icon="glyphicon glyphicon-search" onclick="XJJ.query({id:'${tabId}'});">查询</@button>
	</@query>
	
	
	<#--操作区-->
	<@button type="info" icon="glyphicon glyphicon-plus" onclick="XJJ.add('${base}/sec/manager/input','添加用户','${tabId}');">增加</@button>
	<@button type="purple" icon="fa fa-pencil" onclick="XJJ.edit('${base}/sec/manager/input','修改用户','${tabId}');">修改</@button>
	<@button type="danger" icon=" fa fa-trash-o" onclick="XJJ.del('${base}/sec/manager/delete','删除用户？',true,{id:'${tabId}'});">删除</@button>
	
	<@button type="grey" icon="fa fa-cloud-upload">上传</@button>
</@content>