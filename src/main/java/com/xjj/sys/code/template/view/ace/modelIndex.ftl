${'<#--'}
/****************************************************
 * Description: ${model.label}的列表页面
 * Copyright:   Copyright (c) ${model.year}
 * Company:     ${model.company}
 * @author      ${model.author}
 * @version     ${model.version}
 * @see
	HISTORY
    *  ${model.date} ${model.author} Create File
**************************************************/
${'-->'}
<${'#'}include "/templates/xjj-index.ftl">
<#--导航-->
<${'@'}navList navs=navArr/>

<${'@'}content id=tabId>
	<#--查询区-->
	<${'@'}query queryUrl="${'$'}{base}/${model.requestMapping}/list" id=tabId>
	
		<${'@'}querygroup title='名称'>
			<input type="search" name="query.name@lk@s" class="form-control input-sm" placeholder="请输入名称" aria-controls="dynamic-table">
	    </${'@'}querygroup>
	  	    
		<${'@'}querygroup title='状态'>
			<${'@'}select name="query.status@eq@s" list=XJJConstants.COMMON_STATUS_LIST></${'@'}select>
	    </${'@'}querygroup>
	    
		<${'@'}button type="info" icon="glyphicon glyphicon-search" onclick="XJJ.query({id:'${'$'}{tabId}'});">查询</${'@'}button>
	</${'@'}query>
	
	
	<#--操作区-->
	<${'@'}button type="info" icon="glyphicon glyphicon-plus" onclick="XJJ.add('${'$'}{base}${model.requestMapping?uncap_first}/input','添加${model.label}','${'$'}{tabId}');">增加</${'@'}button>
	<${'@'}button type="purple" icon="fa fa-pencil" onclick="XJJ.edit('${'$'}{base}${model.requestMapping?uncap_first}/input','修改${model.label}','${'$'}{tabId}');">修改</${'@'}button>
	<${'@'}button type="danger" icon=" fa fa-trash-o" onclick="XJJ.del('${'$'}{base}${model.requestMapping?uncap_first}/delete','删除${model.label}？',true,{id:'${'$'}{tabId}'});">删除</${'@'}button>
	
	<${'@'}button type="grey" icon="fa fa-cloud-upload">上传</${'@'}button>
</${'@'}content>