${'<#--'}
/****************************************************
 * Description: ${model.label}的简单列表页面，没有编辑功能
 * Copyright:   Copyright (c) ${model.year}
 * Company:     ${model.company}
 * @author      ${model.author}
 * @version     ${model.version}
 * @see
	HISTORY
    *  ${model.date} ${model.author} Create File
**************************************************/
${'-->'}
<${'#'}include "/templates/xjj-list.ftl"> 
<${'@'}list id=tabId>
	<thead>
		<tr>
			<th><input type="checkbox" class="bscheckall"></th>
			<#list model.fields as field>
			<#if field.propName!="id">
	        <th>${field.columnComment}</th>
	        </#if>
	        </#list>
	        <th>操作</th>
		</tr>
	</thead>
	<tbody>
		<${'#'}list page.items?if_exists as item>
		<tr>
			<td>
			<input type="checkbox" class="bscheck" data="id:${'$'}{item.id}">
			</td>
			<#list model.fields as field>
			<#if field.propName!="id">
			<td>
				<#if field.propType=="Date">
			    ${'$'}{item.${field.propName?uncap_first}?string('yyyy-MM-dd HH:mm:ss')}
			    <#else>
			    ${'$'}{item.${field.propName?uncap_first}}
			    </#if>
			</td>
			</#if>
			</#list>
			<td>
            	<${'@'}button type="purple" icon="fa fa-pencil" onclick="XJJ.edit('${'$'}{base}${model.requestMapping?uncap_first}/input/${'$'}{item.id}','修改${model.label}','${'$'}{tabId}');">修改</${'@'}button>
				<${'@'}button type="danger" icon=" fa fa-trash-o" onclick="XJJ.del('${'$'}{base}${model.requestMapping?uncap_first}/delete/${'$'}{item.id}','删除${model.label}？',false,{id:'${'$'}{tabId}'});">删除</${'@'}button>
            </td>
		</tr>
		</${'#'}list>
	</tbody>
</${'@'}list>