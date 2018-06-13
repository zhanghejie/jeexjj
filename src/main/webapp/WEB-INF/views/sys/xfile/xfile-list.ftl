<#--
/****************************************************
 * Description: t_sys_xfile的简单列表页面，没有编辑功能
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-05-04 zhanghejie Create File
**************************************************/
-->
<#include "/templates/xjj-list.ftl"> 
<@list id=tabId>
	<thead>
		<tr>
			<th><input type="checkbox" class="bscheckall"></th>
	        <th>存储名称</th>
	        <th>文件名称</th>
	        <th>url</th>
	        <th>创建人</th>
	        <th>创建时间</th>
		</tr>
	</thead>
	<tbody>
		<#list page.items?if_exists as item>
		<tr>
			<td>
			<input type="checkbox" class="bscheck" data="id:${item.id}">
			</td>
			<td>
			    ${item.fileRealname}
			</td>
			<td>
			    ${item.fileTitle}
			</td>
			<td>
				${item.url}
            </td>
			<td>
				${item.createUserName}
            </td>
			<td>
			    ${item.createDate?string('yyyy-MM-dd HH:mm:ss')}
			</td>
			
		</tr>
		</#list>
	</tbody>
</@list>