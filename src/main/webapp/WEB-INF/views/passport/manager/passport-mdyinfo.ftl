<#include "/templates/xjj-index.ftl"> 
<@input url="${base}/passport/manager/mdyinfo/save">
    <input type="hidden" name="id" value="${user?if_exists.id}"/>
    <@formgroup title='登录名'>${user?if_exists.loginName}</@formgroup>
    <@formgroup title='中文名'><input type="text" name="userName" value="${user?if_exists.userName}" check-type='required' ></@formgroup>
    <@formgroup title='生日'>
    <@date name="birthday" dateValue=user.birthday required="required" default=true/>
    </@formgroup>
    <@formgroup title='手机'><input type="text" name="mobile" value="${user?if_exists.mobile}"  ></@formgroup>
    <@formgroup title='邮箱'><input type="text" name="email" value="${user?if_exists.email}"  ></@formgroup>
    <@formgroup title='现住地址'><input type="text" name="address" value="${user?if_exists.address}"  ></@formgroup>
</@input>