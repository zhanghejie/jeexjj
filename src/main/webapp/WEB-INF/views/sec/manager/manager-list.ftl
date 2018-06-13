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
			<th>创建时间</th>
			<th>拥有角色</th>
			<th>状态</th>
			<th>操作</th>
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
			<td>${user.createDate?string('yyyy-MM-dd')}</td>
			<td>
				<#if user.loginName=="admin">
					超级管理员
				<#else>
					<#list user.roles?if_exists as r>
					${r.title}
					<a href="javascript:void(0);" action="write" onclick="XJJ.del('${base}/sec/manager/role/cancle/${user.id}/${r.id}','您确定删除分配角色吗？',false,{id:'${tabId}'});">
	           			<font style="color:red"><i class="ace-icon glyphicon glyphicon-remove-sign"></i></font>
	   				</a>
					</#list>
				</#if>
				
			</td>
			<td>
				<span class="label <#if user.status=XJJConstants.COMMON_STATUS_VALID>label-info</#if> arrowed-in arrowed-in-right">${XJJDict.getText(user.status)}</span>
			</td>
			<td>
				<#if user.loginName!="admin">
				<@button type="info" icon="fa fa-user" onclick="XJJ.edit('${base}/sec/manager/role/input/${user.id}','为管理员添加角色','${tabId}');">添加角色</@button>
				</#if>
			</td>
		</tr>
		</#list>
	</tbody>
</@list>