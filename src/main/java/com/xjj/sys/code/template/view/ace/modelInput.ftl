${'<#--'}
/****************************************************
 * Description: ${model.label}的输入页面，包括添加和修改
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

<${'@'}input url="${'$'}{base}${model.requestMapping}/save" id=tabId>
   <input type="hidden" name="id" value="${'$'}{${model.name?uncap_first}.id}"/>
   
<#list model.fields as field>
   <#if field.propName!="id">
   <${'@'}formgroup title='${field.columnComment}'>
   <#if field.propType=="Date">
	<${'@'}date name="${field.propName?uncap_first}" dateValue=${model.name?uncap_first}.${field.propName?uncap_first} <#if field.required>required="required"</#if> default=true/>
   <#else>
	<input type="text" name="${field.propName?uncap_first}" value="${'$'}{${model.name?uncap_first}.${field.propName?uncap_first}}" <#if field.checkType!="">check-type="${field.checkType}"</#if>>
   </#if>
   </${'@'}formgroup>
   </#if>
</#list>
</${'@'}input>