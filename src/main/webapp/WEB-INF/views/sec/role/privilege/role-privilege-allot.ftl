<#include "/templates/xjj-index.ftl"> 
<script>
	//单击菜单权限
	function checkSelect(treeNodePId){
		var parentSec = document.getElementById(treeNodePId);
		var functionArr = [];
		console.info("parentSec.checked="+treeNodePId+"="+parentSec.checked);
		if(parentSec.checked){
			var childSecs = getElementsByClassName(treeNodePId);
			for(var i=0;i<childSecs.length;i++){
			
				if(childSecs[i].checked==false)
				{
					functionArr.push(childSecs[i].value);
					childSecs[i].checked = true;
				}
			}
		}else{
			var childSecs = getElementsByClassName(treeNodePId);
			for(var i=0;i<childSecs.length;i++){
				if(childSecs[i].checked==true)
				{
					functionArr.push(childSecs[i].value);
					childSecs[i].checked = false;
				}
			}
		}
		
		//ajax权限设置
		setPri(treeNodePId,functionArr.join("|"),parentSec.checked);
	}
	
	//单击按钮权限
	function checkSelect1(treeNodePId,treeNodeCId){
		var parentSec = document.getElementById(treeNodePId);
		var childSec = document.getElementById(treeNodePId+"_"+treeNodeCId);
		if(!childSec.checked){
			parentSec.checked = false;
			
		}else{
			var childSecs = getElementsByClassName(treeNodePId);
			var count = 0;
			for(var i=0;i<childSecs.length;i++){
				if(childSecs[i].checked == true){
					count++;
				}
			}
			if(count == childSecs.length){
				parentSec.checked = true;
			}
		}
		
		//ajax权限设置
		setPri(treeNodePId,treeNodeCId,childSec.checked);
	}
	function getElementsByClassName(className) {
		var classElements = [],allElements = document.getElementsByTagName('*');
		for (var i=0; i< allElements.length; i++ ){
			 if (allElements[i].className == className ) {
			 	classElements[classElements.length] = allElements[i];
			 }
		}
		return classElements;
	}
	$(function(){
		$(":input").each(function(){
			if($(this).val()=='true'){
				document.getElementById($(this).prop("class")).checked = true;
			};
		});
	});
	
	function setPri(priCode,functions,isChecked)
	{
		var url;
		if(isChecked)
		{
			url = "${base}/sec/role/privilege/add/${roleId}/"+priCode+"/"+functions;
		}else
		{
			url = "${base}/sec/role/privilege/cancle/${roleId}/"+priCode+"/"+functions;
		}
		$.ajax({
			type: "post",
			url: url,
			success: function(data){
			  	if(data.message != '' && data.result == 'success'){
					XJJ.msg(data.message);
				}else{
					XJJ.msger(data.message);
				}
			}
		});
	}
</script>
<@input>
	<input type="hidden" name="roleId" value="${roleId}"/>
	<div class="table-responsive">
		<table class='table'>
			<tbody>
		<#list treeNodeList?sort_by('text') as treeNode>
			<#if (treeNode.nodes?if_exists?size>0)>
				<#list treeNode.nodes?sort_by('text') as treeNode1>
					<tr>
						<td>
							<input type="hidden" class="menu_${treeNode1.id}" value="${treeNode1.checked}"/>
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="menu_${treeNode1.id}" onclick="checkSelect('menu_${treeNode1.id}')" >
							${treeNode1.text}
				        </td>
					</tr>
					<#if (treeNode1.nodes?if_exists?size>0)>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<#list treeNode1.nodes?sort_by('text') as treeNode2>
								<input type="hidden" name="menu_${treeNode1.id}" class="menu_${treeNode1.id}_${treeNode2.id}" value="${treeNode2.checked}"/>
								&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="menu_${treeNode1.id}_${treeNode2.id}" name="codes" class="menu_${treeNode1.id}" value="${treeNode2.id}" onclick="checkSelect1('menu_${treeNode1.id}','${treeNode2.id}')" >
								${treeNode2.text}
								</#list>
					        </td>
						</tr>
					</#if>
				</#list>
			</#if>
		</#list>
			</tbody>
		</table>
	</div>
</@input>


