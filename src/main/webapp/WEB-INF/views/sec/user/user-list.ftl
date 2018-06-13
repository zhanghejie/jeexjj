<#include "/templates/xjj-list.ftl"> 
<@list id=tabId>
	<thead>
		<tr>
			 <th><input type="checkbox" class="bscheckall"></th>
			<th>账号</th>
			<th>用户类型</th>
			<th>姓名</th>
			<th>手机</th>
			<th>邮箱</th>
			<th>省份</th>
			<th>生日</th>
			<th>注册日期</th>
			<th>状态</th>
		</tr>
	</thead>
	<tbody>
		<#list page.items?if_exists as user>
		<tr>
			<td><input type="checkbox" class="bscheck" data="id:${user.id}"></td>
			<td>${user.loginName}</td>
			<td>
				<#if user.userType??>
					${XJJDict.getText(user.userType)}
				</#if>
			</td>
			<td>${user.userName}</td>
			<td>${user.mobile}</td>
			<td>${user.email}</td>
			<td>${DictConstants.getDictName(DictConstants.DICT_PROVINCE,user.province)}</td>
			<td><#if user.birthday??>${user.birthday?string('yyyy-MM-dd')}</#if></td>
			<td>${user.createDate?string('yyyy-MM-dd')}</td>
			<td>
				<span class="label <#if user.status=XJJConstants.COMMON_STATUS_VALID>label-info</#if> arrowed-in arrowed-in-right">${XJJDict.getText(user.status)}</span>
			</td>
		</tr>
		</#list>
	</tbody>
</@list>