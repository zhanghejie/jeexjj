<#--
/****************************************************
 * Description: t_sec_role_privilege的输入页面，包括添加和修改
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

<@input url="${base}/sec/role/privilege/save" id=tabId>
   <input type="hidden" name="id" value="${rolePrivilege.id}"/>
   
   <@formgroup title='ROLE_ID'>
	<input type="text" name="roleId" value="${rolePrivilege.roleId}" check-type="number">
   </@formgroup>
   <@formgroup title='PRIVILEGE_TITLE'>
	<input type="text" name="privilegeTitle" value="${rolePrivilege.privilegeTitle}" >
   </@formgroup>
   <@formgroup title='PRIVILEGE_CODE'>
	<input type="text" name="privilegeCode" value="${rolePrivilege.privilegeCode}" >
   </@formgroup>
   <@formgroup title='FUNCTION_LIST'>
	<input type="text" name="functionList" value="${rolePrivilege.functionList}" >
   </@formgroup>
</@input>