<#--
/****************************************************
 * Description: 菜单的输入页面，包括添加和修改
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-12 zhanghejie Create File
**************************************************/
-->
<#include "/templates/xjj-index.ftl"> 

<@input url="${base}/sec/menu/save" id=tabId>
   <input type="hidden" name="id" value="${menu.id}"/>
   <@formgroup title='父菜单'>
	<#if parentMenu?exists>
	${parentMenu.title}
	<input type="hidden" name="parentId" value="${parentMenu.id}">
	<input type="hidden" name="parentCode" value="${parentMenu.code}">
	<#else>根菜单</#if>
   </@formgroup>
   
   <@formgroup title='名称'>
	<input type="text" name="title" value="${menu.title}" check-type="required">
   </@formgroup>
   <@formgroup title='描述'>
	<input type="text" name="description" value="${menu.description}" >
   </@formgroup>
   
   <@formgroup title='对应权限'>
		<@select name="privilegeCode" value="${menu.privilegeCode}" list=privileges listKey='title' listValue='code' sortBy='title'/>
   </@formgroup>
   
   <@formgroup title='编号'>
	<input type="text" name="code" value="${menu.code}" check-type="required number">
   </@formgroup>
   <#if parentMenu==null>
   <@formgroup title='图标'>
	<table>
		<tr>
		
		<td><input type="radio" name="icon" value="glass"><i class="fa fa-glass"></td></i>
		<td><input type="radio" name="icon" value="music"><i class="fa fa-music"></td></i>
		<td><input type="radio" name="icon" value="search"><i class="fa fa-search"></td></i>
		<td><input type="radio" name="icon" value="envelope-o"><i class="fa fa-envelope-o"></td></i>
		<td><input type="radio" name="icon" value="heart"><i class="fa fa-heart"></td></i>
		<td><input type="radio" name="icon" value="star"><i class="fa fa-star"></td></i>
		<td><input type="radio" name="icon" value="user"><i class="fa fa-user"></td></i>
		<td><input type="radio" name="icon" value="film"><i class="fa fa-film"></td></i>
		<td><input type="radio" name="icon" value="search-plus"><i class="fa fa-search-plus"></td></i>
		<td><input type="radio" name="icon" value="cloud"><i class="fa fa-cloud"></td></i>
		</tr>
		<tr>
		<td><input type="radio" name="icon" value="power-off"><i class="fa fa-power-off"></td></i>
		<td><input type="radio" name="icon" value="desktop"><i class="fa fa-desktop"></td></i>
		<td><input type="radio" name="icon" value="signal"><i class="fa fa-signal"></td></i>
		<td><input type="radio" name="icon" value="home"><i class="fa fa-home"></td></i>
		<td><input type="radio" name="icon" value="inbox"><i class="fa fa-inbox"></td></i>
		<td><input type="radio" name="icon" value="repeat"><i class="fa fa-repeat"></td></i>
		<td><input type="radio" name="icon" value="lock"><i class="fa fa-lock"></td></i>
		<td><input type="radio" name="icon" value="cog"><i class="fa fa-cog"></td></i>
		<td><input type="radio" name="icon" value="list-alt"><i class="fa fa-list-alt"></td></i>
		<td><input type="radio" name="icon" value="flag"><i class="fa fa-flag"></td></i>
		</tr>
		<tr>
		<td><input type="radio" name="icon" value="qrcode"><i class="fa fa-qrcode"></td></i>
		<td><input type="radio" name="icon" value="book"><i class="fa fa-book"></td></i>
		<td><input type="radio" name="icon" value="bell-o"><i class="fa fa-bell-o"></td></i>
		<td><input type="radio" name="icon" value="mortar-board"><i class="fa fa-mortar-board"></td></i>
		<td><input type="radio" name="icon" value="file"><i class="fa fa-file"></td></i>
		<td><input type="radio" name="icon" value="line-chart"><i class="fa fa-line-chart"></td></i>
		<td><input type="radio" name="icon" value="users"><i class="fa fa-users"></td></i>
		<td><input type="radio" name="icon" value="tags"><i class="fa fa-tags"></td></i>
		<td><input type="radio" name="icon" value="database"><i class="fa fa-database"></td></i>
		<td><input type="radio" name="icon" value="comment-o"><i class="fa fa-comment-o"></td></i>
		</tr>
		<tr>
		<td><input type="radio" name="icon" value="video-camera"><i class="fa fa-video-camera"></td></i>
		<td><input type="radio" name="icon" value="upload"><i class="fa fa-upload"></td></i>
		<td><input type="radio" name="icon" value="wrench"><i class="fa fa-wrench"></td></i>
		<td><input type="radio" name="icon" value="table"><i class="fa fa-table"></td></i>
		<td><input type="radio" name="icon" value="share-alt"><i class="fa fa-share-alt"></td></i>
		<td><input type="radio" name="icon" value="send"><i class="fa fa-send"></td></i>
		<td><input type="radio" name="icon" value="print"><i class="fa fa-print"></td></i>
		<td><input type="radio" name="icon" value="question"><i class="fa fa-question"></td></i>
		<td><input type="radio" name="icon" value="pie-chart"><i class="fa fa-pie-chart"></td></i>
		<td><input type="radio" name="icon" value="credit-card"><i class="fa fa-credit-card"></td></i>
		</tr>
	</table>
   </@formgroup>
   </#if>
   <@formgroup title='状态'>
		<@swichInForm name="status" val=menu.status onTitle="有效" offTitle="无效"></@swichInForm>
    </@formgroup>
</@input>

<script type="text/javascript">
$(function(){

	<#if menu.icon?exists>
	$("input[value='${menu.icon}']").attr("checked","checked");
	$("input[value='${menu.icon}']").checked=true;
	</#if>
});
</script>