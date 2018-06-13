<#include "/templates/xjj-list.ftl">
<table class='table table-responsive'>
	<thead>
		<th>名称</th>
		<th>编码</th>
		<th>地址</th>
		<th>编号</th>
		<th>状态</th>
		<th>操作</th>
	</thead>
	<tbody>
<#list menuList as m>
	<tr>
		<td>
			<#if m.subMenus?? && m.subMenus?size gt 0><a href="javascript:void(0)" onclick="javascript:toggle(${m.id},this)" id="a${m.id}"><span class="glyphicon glyphicon-minus"></span></a></#if>
			${m.title}<i class="ace-icon fa fa-${m.icon} bigger-100"></i>
		</td>
		<td>${m.privilegeCode}</td>
		<td>${m.url}</td>
		<td>${m.code}</td>
		<td>
		${XJJDict.getText(m.status)}
		</td>
		<td>
			<div class="hidden-sm hidden-xs btn-group">
				<@button type="info" icon="fa fa-pencil" onclick="XJJ.edit('${base}/sec/menu/input/${m.id}','修改菜单','${tabId}');" bigger="120"></@button>
				<@button type="danger" icon="fa fa-trash-o" onclick="XJJ.del('${base}/sec/menu/delete/${m.id}','删除菜单？',false,{id:'${tabId}'});" bigger="120"></@button>
				
				<#if m.status=XJJConstants.COMMON_STATUS_VALID>
					<@button type="warning" icon="fa fa-ban" onclick="XJJ.del('${base}/sec/menu/disable/${m.id}','禁用菜单？',false,{id:'${tabId}'});" bigger="120"></@button>
				<#else>
					<@button type="success" icon="fa fa-check" onclick="XJJ.del('${base}/sec/menu/enable/${m.id}','启用菜单？',false,{id:'${tabId}'});" bigger="120"></@button>
				</#if>
				<@button type="success" icon="glyphicon glyphicon-plus" onclick="XJJ.add('${base}/sec/menu/${m.id}/input','添加子菜单','${tabId}');" bigger="118"></@button>
			</div>
		</td>
	</tr>
	<#if m.subMenus?? && m.subMenus?size gt 0>
		<#list m.subMenus as mm>
		<tr class="${m.id}">
			<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<#if mm.subMenus?? && mm.subMenus?size gt 0><a href="javascript:void(0)" onclick="javascript:toggle(${mm.id},this)" id="a${mm.id}"><span class="glyphicon glyphicon-minus"></span></a></#if>${mm.title}</td>
			<td>${mm.privilegeCode}</td>
			<td>${mm.url}</td>
			<td>${mm.code}</td>
			<td>
				${XJJDict.getText(mm.status)}
			</td>
			<td>
				<div class="hidden-sm hidden-xs btn-group">
					<#--
					<@button type="success" icon="glyphicon glyphicon-plus" onclick="XJJ.add('${base}/sec/menu/${mm.id}/input','添加子菜单','${tabId}');" bigger="118"></@button>
					-->
					<@button type="info" icon="fa fa-pencil" onclick="XJJ.edit('${base}/sec/menu/input/${mm.id}','修改菜单','${tabId}');" bigger="120"></@button>
					<@button type="danger" icon="fa fa-trash-o" onclick="XJJ.del('${base}/sec/menu/delete/${mm.id}','删除菜单？',false,{id:'${tabId}'});" bigger="120"></@button>
					
					<#if mm.status=XJJConstants.COMMON_STATUS_VALID>
						<@button type="warning" icon="fa fa-ban" onclick="XJJ.del('${base}/sec/menu/disable/${mm.id}','禁用菜单？',false,{id:'${tabId}'});" bigger="120"></@button>
					<#else>
						<@button type="success" icon="fa fa-check" onclick="XJJ.del('${base}/sec/menu/enable/${mm.id}','启用菜单？',false,{id:'${tabId}'});" bigger="120"></@button>
					</#if>
				</div>
			</td>
		</tr>
			<#if mm.subMenus?? && mm.subMenus?size gt 0>
				<#list mm.subMenus as mmm>
				<tr class="${mm.id}">
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${mmm.title}</td>
					<td>${mmm.privilegeCode}</td>
					<td>${mmm.url}</td>
					<td>${mmm.code}</td>
					<td>
					${XJJDict.getText(mmm.status)}
					</td>
			
					<td>
						<div class="hidden-sm hidden-xs btn-group">
							<@button type="success" icon="glyphicon glyphicon-plus" onclick="XJJ.add('${base}/sec/menu/${mmm.id}/input','添加子菜单','${tabId}');" bigger="118"></@button>
							<@button type="info" icon="fa fa-pencil" onclick="XJJ.edit('${base}/sec/menu/input/${mmm.id}','修改菜单','${tabId}');" bigger="120"></@button>
							<@button type="danger" icon="fa fa-trash-o" onclick="XJJ.del('${base}/sec/menu/delete/${mmm.id}','删除菜单？',false,{id:'${tabId}'});" bigger="120"></@button>
							
							<#if mmm.status=XJJConstants.COMMON_STATUS_VALID>
								<@button type="warning" icon="fa fa-ban" onclick="XJJ.del('${base}/sec/menu/disable/${mmm.id}','禁用菜单？',false,{id:'${tabId}'});" bigger="120"></@button>
							<#else>
								<@button type="success" icon="fa fa-check" onclick="XJJ.del('${base}/sec/menu/enable/${mmm.id}','启用菜单？',false,{id:'${tabId}'});" bigger="120"></@button>
							</#if>
						</div>
					</td>
				</tr>
				</#list>
			</#if>
		</#list>
	</#if>
</#list>
	</tbody>
</table>