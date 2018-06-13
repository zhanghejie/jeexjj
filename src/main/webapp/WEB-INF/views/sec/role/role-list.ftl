<#--
/****************************************************
 * Description: 角色的简单列表页面，没有编辑功能
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/
-->
<#include "/templates/xjj-list.ftl"> 
<@list id=tabId>
	<thead>
		<tr>
			<th><input type="checkbox" class="bscheckall"></th>
	        <th>名称</th>
	        <th>编码</th>
	        <th>描述</th>
	        <th>状态</th>
	        <th>操作</th>
		</tr>
	</thead>
	<tbody>
		<#list page.items?if_exists as item>
		<tr>
			<td>
			<input type="checkbox" class="bscheck" data="id:${item.id}">
			</td>
			<td>
			    ${item.title}
			</td>
			<td>
			    ${item.code}
			</td>
			<td>
			    ${item.description}
			</td>
			<td>
			    <span class="label <#if item.status=XJJConstants.COMMON_STATUS_VALID>label-info</#if> arrowed-in arrowed-in-right">${XJJDict.getText(item.status)}</span>
			</td>
			<td>
				<@button type="info" icon="fa fa-user" onclick="XJJ.view('${base}/sec/role/privilege/allot/${item.id}','为【${item.title}】角色设置权限','${tabId}');">设置权限</@button>
            </td>
		</tr>
		</#list>
	</tbody>
</@list>