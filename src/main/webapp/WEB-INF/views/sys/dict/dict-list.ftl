<#include "/templates/xjj-list.ftl"> 
<@list id=tabId>
	<thead>
		<tr>
			 <th><input type="checkbox" class="bscheckall"></th>
			<th>属组</th>
			<th>编码</th>
			<th>名称</th>
			<th>序号</th>
			<th>状态</th>
		</tr>
	</thead>
	<tbody>
		<#list page.items?if_exists as dict>
		<tr>
			<td><input type="checkbox" class="bscheck" data="id:${dict.id}"></td>
			<td>
			${XJJDict.getText(dict.groupCode)}
			</td>
			<td>${dict.code}</td>
			<td>${dict.name}</td>
			<td>${dict.sn}</td>
			<td>
				<span class="label <#if dict.status=XJJConstants.COMMON_STATUS_VALID>label-info</#if> arrowed-in arrowed-in-right">${XJJDict.getText(dict.status)}</span>
			</td>
		</tr>
		</#list>
	</tbody>
</@list>