<#include "/templates/xjj-index.ftl">
<#--导航-->
<@navList navs=navArr/>
<@content id=tabId>
	<#--查询区-->
	<@query queryUrl="${base}/sec/user/list" id=tabId>
	
		<@querygroup title='账号'>
			<input type="search" name="query.loginName@lk@s" class="form-control input-sm" placeholder="请输入账号" aria-controls="dynamic-table">
	    </@querygroup>
		<@querygroup title='姓名'>
			<input type="search" name="query.userName@lk@s" class="form-control input-sm" placeholder="请输入姓名" aria-controls="dynamic-table">
	    </@querygroup>
	    
		<@querygroup title='手机'>
			<input type="search" name="query.mobile@eq@s" class="form-control input-sm" placeholder="请输入手机号" aria-controls="dynamic-table">
	    </@querygroup>
		<@querygroup title='邮箱'>
			<input type="search" name="query.email@eq@s" class="form-control input-sm" placeholder="请输入邮箱" aria-controls="dynamic-table">
	    </@querygroup>
	    <@querygroup title='状态'>
			<@select name="query.status@eq@s" list=XJJConstants.COMMON_STATUS_LIST></@select>
	    </@querygroup>
		<@querygroup title='生日'>
			 <input type="search" name="query.birthday@ge@D" class="date form-control date-picker input-sm" type="text" placeholder="请选择开始日期">
	    </@querygroup>
		<@querygroup title='生日'>
			 <input type="search" name="query.birthday@le@D" class="date form-control date-picker input-sm" type="text" placeholder="请选择结束日期">
	    </@querygroup>
	    
		<@button type="info" icon="glyphicon glyphicon-search" onclick="XJJ.query({id:'${tabId}'});">查询</@button>
	</@query>
	
	
	<#--操作区-->
	<@button type="info" icon="glyphicon glyphicon-plus" onclick="XJJ.add('${base}/sec/user/input','添加用户','${tabId}');" funcCode="create">增加</@button>
	<@button type="purple" icon="fa fa-pencil" onclick="XJJ.edit('${base}/sec/user/input','修改用户','${tabId}');">修改</@button>
	<@button type="danger" icon=" fa fa-trash-o" onclick="XJJ.del('${base}/sec/user/delete','删除用户？',true,{id:'${tabId}'});">删除</@button>
	
	<@button type="success" icon="fa fa-cloud-upload" onclick="XJJ.add('${base}/sec/user/import','导入用户','${tabId}');">导入</@button>
	<@button type="success" icon="fa fa-cloud-download" onclick="window.location.href='${base}/sec/user/export/excel';">导出</@button>
</@content>