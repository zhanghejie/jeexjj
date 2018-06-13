<#--
/****************************************************
 * Description: t_sec_role_privilege的简单列表页面，没有编辑功能
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
	        <th>ROLE_ID</th>
	        <th>PRIVILEGE_TITLE</th>
	        <th>PRIVILEGE_CODE</th>
	        <th>FUNCTION_LIST</th>
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
			    ${item.roleId}
			</td>
			<td>
			    ${item.privilegeTitle}
			</td>
			<td>
			    ${item.privilegeCode}
			</td>
			<td>
			    ${item.functionList}
			</td>
			<td>
            	<@button type="purple" icon="fa fa-pencil" onclick="XJJ.edit('${base}/sec/role/privilege/input/${item.id}','修改t_sec_role_privilege','${tabId}');">修改</@button>
				<@button type="danger" icon=" fa fa-trash-o" onclick="XJJ.del('${base}/sec/role/privilege/delete/${item.id}','删除t_sec_role_privilege？',false,{id:'${tabId}'});">删除</@button>
            </td>
		</tr>
		</#list>
	</tbody>
</@list>