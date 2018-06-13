<#include "/templates/xjj-global.ftl">


<#-- list列表 -->
<#macro list id='' showPage=true>
	<table class="table table-bordered table-hover dataTable no-footer">
	<#nested>
	</table>	
	<#if showPage>
		<@pagination page=page id=id></@pagination>
	</#if>
</#macro>


<#-- pagination 分页-->
<#macro pagination page id=''>
	<div class="row">
	<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6" style='padding-left:0px;padding-right:0px;'>
		<div class="input-group">
			<span class="input-group-addon">第</span>
			<input type="text" class="form-control crudpage" value="${page.currentPage}">
			<span class="input-group-addon">页每页</span>
			<input type="text" class="form-control crudsize" value="${page.pageSize}">
			<span class="input-group-addon">条</span>
			<span class="input-group-addon">共&nbsp;${page.totalRecord}&nbsp;条&nbsp;${page.totalPage}&nbsp;页</span>
			<button class="btn btn-info form-control" onclick="javascript:XJJ.go({id:'${id}'});">GO</button>
		</div>
	</div>
	<div class="col-xs-6">
		<div class="dataTables_paginate paging_simple_numbers"
			id="dynamic-table_paginate">
			
			<ul class="pagination" style="margin:0;">
			<#assign pagenum = page.totalPage>
			<#if pagenum gt 1>
				<li <#if page.currentPage == 1>class="disabled"</#if>>
					<a href="javascript:XJJ.page({id:'${id}',currentPage:1});" class="crud crudfirst">
						<@bsicon icon="step-backward"/>
					</a>
				</li>
				<li <#if page.currentPage == 1>class="disabled"</#if>>
					<a href="javascript:XJJ.page({id:'${id}',currentPage:${page.currentPage-1}});" class="crud crudprev">
						<@bsicon icon="chevron-left"/>
					</a>
				</li>
				<#if pagenum lte 3>
					<#list 1..pagenum as pn>
						<li <#if page.currentPage == pn>class="active"</#if>>
							<a href="javascript:XJJ.page({id:'${id}',currentPage:${pn}});" class="cruda">${pn}</a>
						</li>
					</#list>
				</#if>
				<#if pagenum gt 3>
					<#if page.currentPage lt 2>
						<#list 1..pagenum as pn>
							<#if pn lt 3>
								<li <#if page.currentPage == pn>class="active"</#if>>
									<a href="javascript:XJJ.page({id:'${id}',currentPage:${pn}});" class="cruda">${pn}</a>
								</li>
							</#if>
								
						</#list>
					</#if>
					<#if page.currentPage gte 2>
						<#if page.currentPage-1 gt 0>
							<li class="disabled">
								<a href="javascript:void(0);">...</a>
							</li>
							
						</#if>
						<#list 1..pagenum as pn>
							<#if (page.currentPage-1 <= pn)&&(pn <= page.currentPage+1)>
								<li <#if page.currentPage == pn>class="active"</#if>>
									<a href="javascript:XJJ.page({id:'${id}',currentPage:${pn}});" class="cruda">${pn}</a>
								</li>
							</#if>
								
						</#list>
					</#if>
					<#if page.currentPage+1 lt pagenum>
						<li class="disabled">
							<a href="javascript:void(0);">...</a>
						</li>
					</#if>
				</#if>
				
				<li <#if page.currentPage == pagenum>class="disabled"</#if>>
					<a href="javascript:XJJ.page({id:'${id}',currentPage:${page.currentPage+1}});" class="crud crudnext">
						<@bsicon icon="chevron-right"/>
					</a>
				</li>
				<li	<#if page.currentPage == pagenum>class="disabled"</#if>>
					<a href="javascript:XJJ.page({id:'${id}',currentPage:${page.totalPage}});" class="crud crudlast" data="page:${pagenum}">
						<@bsicon icon="step-forward"/>
					</a>
				</li>
			</#if>
		</ul>
		</div>
	</div>
	</div>
</#macro>
