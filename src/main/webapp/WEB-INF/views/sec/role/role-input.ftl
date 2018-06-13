<#--
/****************************************************
 * Description: 角色的输入页面，包括添加和修改
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

<@input url="${base}/sec/role/save" id=tabId>
   <input type="hidden" name="id" value="${role.id}"/>
   
   <@formgroup title='名称'>
	<input type="text" name="title" value="${role.title}" check-type="required">
   </@formgroup>
   <@formgroup title='编码'>
	<input type="text" name="code" value="${role.code}" check-type="required">
   </@formgroup>
   <@formgroup title='描述'>
	<input type="text" name="description" value="${role.description}">
   </@formgroup>
   
   <@formgroup title='状态'>
		<@swichInForm name="status" val=role.status onTitle="有效" offTitle="无效"></@swichInForm>
   </@formgroup>
</@input>