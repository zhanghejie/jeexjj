<#include "/templates/xjj-index.ftl"> 

<@input url="${base}/sec/manager/role/save" id=tabId>
	<input type="hidden" name="userId" value="${user.id}"/>
    <@formgroup title='账号'><input type="text" value="${user.loginName}" readonly="readonly"></@formgroup>
    <@formgroup title='姓名'><input type="text" value="${user.userName}" readonly="readonly"></@formgroup>
    <@formgroup title='角色'>
    	<div class='checkbox-inline'>
    	<#list roleList as role>
    		<input type="checkbox" name="roleIds" value="${role.id}">${role.title}<br/>
    	</#list>
    	</div>
    </@formgroup>
</@input>