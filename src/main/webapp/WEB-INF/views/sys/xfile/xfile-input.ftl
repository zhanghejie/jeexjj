<#--
/****************************************************
 * Description: t_sys_xfile的输入页面，包括添加和修改
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-05-04 zhanghejie Create File
**************************************************/
-->
<#include "/templates/xjj-index.ftl"> 

<@input url="${base}/sys/xfile/save" id=tabId>
   <input type="hidden" name="id" value="${xfile.id}"/>
   
   <@formgroup title='系统名称'>
	<input type="text" name="fileRealname" value="${xfile.fileRealname}" check-type="required">
   </@formgroup>
   <@formgroup title='文件路径'>
	<input type="text" name="filePath" value="${xfile.filePath}" check-type="required">
   </@formgroup>
   <@formgroup title='文件名称'>
	<input type="text" name="fileTitle" value="${xfile.fileTitle}" check-type="required">
   </@formgroup>
  
</@input>