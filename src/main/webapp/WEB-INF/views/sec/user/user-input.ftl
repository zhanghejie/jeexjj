<#include "/templates/xjj-index.ftl"> 

<@input url="${base}/sec/user/save" id=tabId>
    <input type="hidden" name="id" value="${user.id}"/>
    <@formgroup title='账号'><input type="text" name="loginName" value="${user.loginName}" check-type='required'></@formgroup>
    <@formgroup title='姓名'><input type="text" name="userName" value="${user.userName}" ></@formgroup>
    <@formgroup title='手机'><input type="text" name="mobile" value="${user.mobile}" ></@formgroup>
    <@formgroup title='邮箱'><input type="text" name="email" value="${user.email}" ></@formgroup>
    <@formgroup title='积分'><input type="text" name="integral" value="" check-type='number'></@formgroup>
    <@formgroup title='省份'>
    	<@select name="province" list=dictList listKey="name" listValue="code" value=user.province></@select>
    </@formgroup>
    <@formgroup title='生日'>
    	<@date name="birthday" dateValue=user.birthday required="required" default=true/>
    </@formgroup>
    <@formgroup title='状态'>
		<@swichInForm name="status" val=user.status onTitle="有效" offTitle="无效"></@swichInForm>
    </@formgroup>
    
</@input>