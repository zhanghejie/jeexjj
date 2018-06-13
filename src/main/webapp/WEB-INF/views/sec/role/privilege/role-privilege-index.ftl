<#--
/****************************************************
 * Description: t_sec_role_privilege的列表页面
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/
-->
<#include "/templates/xjj-index.ftl">
<@nav '新境界','t_sec_role_privilege'/>

<@content id=tabId>
	<@query queryUrl="${base}//sec/role/privilege/list" id=tabId>
	
		<@querygroup title='名称'>
			<input type="search" name="query.name@s" class="form-control input-sm" placeholder="请输入名称" aria-controls="dynamic-table">
	    </@querygroup>
	  	    
		<@querygroup title='状态'>
			<@select name="query.status@s" list=XJJConstants.COMMON_STATUS_LIST></@select>
	    </@querygroup>
	    
		<@button type="info" icon="glyphicon glyphicon-search" onclick="XJJ.query({id:'${tabId}'});">查询</@button>
	</@query>
	
	
	<@button type="info" icon="glyphicon glyphicon-plus" onclick="XJJ.add('${base}/sec/role/privilege/input','添加t_sec_role_privilege','${tabId}');">增加</@button>
	<@button type="purple" icon="fa fa-pencil" onclick="XJJ.edit('${base}/sec/role/privilege/input','修改t_sec_role_privilege','${tabId}');">修改</@button>
	<@button type="danger" icon=" fa fa-trash-o" onclick="XJJ.del('${base}/sec/role/privilege/delete','删除t_sec_role_privilege？',true,{id:'${tabId}'});">删除</@button>
	
	<@button type="grey" icon="fa fa-cloud-upload">上传</@button>
</@content>