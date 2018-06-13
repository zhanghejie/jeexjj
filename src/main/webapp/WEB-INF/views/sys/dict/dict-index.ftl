<#include "/templates/xjj-index.ftl">
<#--导航-->
<@navList navs=navArr/>

<@content id=tabId>
	
	<#--查询区-->
	<@query queryUrl="${base}/sys/dict/list" id=tabId>
	
		<@querygroup title='名称'>
			<input type="search" name="query.name@lk@s" class="form-control input-sm" placeholder="请输入名称" aria-controls="dynamic-table">
	    </@querygroup>
	    
		<@querygroup title='编码'>
			<input type="search" name="query.code@eq@s" class="form-control input-sm" placeholder="请输入编码" aria-controls="dynamic-table">
	    </@querygroup>
	    
	    <@querygroup title='字典组'>
    		<@select name="query.groupCode@eq@s" list=DictConstants.DICT_GROUP></@select>
    	</@querygroup>
    
	    
	    
		<@querygroup title='状态'>
			<@select name="query.status@eq@s" list=XJJConstants.COMMON_STATUS_LIST></@select>
	    </@querygroup>
	    
		<@button type="info" icon="glyphicon glyphicon-search" onclick="XJJ.query({id:'${tabId}'});">查询</@button>
	</@query>
	
	
	<#--操作区-->
	<@button type="info" icon="glyphicon glyphicon-plus" onclick="XJJ.add('${base}/sys/dict/input','添加字典','${tabId}');" funcCode="create">增加</@button>
	<@button type="purple" icon="fa fa-pencil" onclick="XJJ.edit('${base}/sys/dict/input','修改字典','${tabId}');" funcCode="update">修改</@button>
	<@button type="danger" icon=" fa fa-trash-o" onclick="XJJ.del('${base}/sys/dict/delete','删除字典？',true,{id:'${tabId}'});" funcCode="del">删除</@button>
</@content>