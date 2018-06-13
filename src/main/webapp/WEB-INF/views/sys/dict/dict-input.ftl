<#include "/templates/xjj-index.ftl"> 

<@input url="${base}/sys/dict/save" id=tabId>
    <input type="hidden" name="id" value="${dict.id}"/>
    <@formgroup title='字典组'>
    	<@select name="groupCode" list=DictConstants.DICT_GROUP value=dict.groupCode></@select>
    </@formgroup>
    <@formgroup title='名称'><input type="text" name="name" value="${dict.name}" ></@formgroup>
    <@formgroup title='编码'><input type="text" name="code" value="${dict.code}" ></@formgroup>
    <@formgroup title='序号'><input type="text" name="sn" value="${dict.sn}" check-type='number'></@formgroup>
    <@formgroup title='状态'>
		<@swichInForm name="status" val=dict.status onTitle="有效" offTitle="无效"></@swichInForm>
    </@formgroup>
    
</@input>