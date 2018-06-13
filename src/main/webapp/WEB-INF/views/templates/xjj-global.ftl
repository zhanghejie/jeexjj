<#assign XJJDict=localizedTextContext>
<#-- aceicon图标封装 -->
<#macro aceicon icon='' bigger='125'>
    <i class="ace-icon ${icon} align-top bigger-${bigger}"></i>
</#macro>

<#-- bootstrap图标封装 -->
<#macro bsicon icon=''>
	<#if icon != ''>
    <span class="glyphicon glyphicon-${icon}"></span>
	</#if>
</#macro>

<#--
	行封装，和container配套使用
	fit	    是否填充满container，默认为false
	id		id
	data	一些数据可以放到这里，通过js读取
-->
<#macro row fit=true id='' data=''>
<div class="row<#if fit>-fluid</#if>" <#if id != ''>id="${id}"</#if> <#if data != ''>data="${data}"</#if>>
	<#nested>
</div>
</#macro>

<#macro array2str array><#if array?exists><#if array?is_sequence>[<#list array as item>${item}<#if item_has_next>,</#if></#list>]<#else><#if array!=''>${array}<#else>''</#if></#if></#if></#macro>

<#function equal data value>
	<#if data?is_sequence>
		<#list data as d>
			<#if d==value><#return true></#if>
		</#list>
	</#if>
	<#if !data?is_sequence && data==value>
		<#return true>
	</#if>
	<#return false>
</#function>
<#-- button
	按钮
	type	类型，用来显示不同的颜色
	size	按钮大小
	icon	按钮图标
	onclick	点击事件
-->
<#macro button type="info" size='xs' icon='' onclick='' disabled='' bigger='125' funcCode=''>
	<#if tabId?exists && funcCode != ''>
		<#if SecurityCache.hasPrivilege(Request,tabId,funcCode)>
			<button type="button" class="btn btn-${type} btn-${size}" <#if onclick != ''>onclick="${onclick}"</#if><#if disabled != ''>disabled="${disabled}"</#if>>
				<@aceicon icon=icon bigger=bigger/>
				<#nested>
			</button>
		</#if>
	<#else>
	<button type="button" class="btn btn-${type} btn-${size}" <#if onclick != ''>onclick="${onclick}"</#if><#if disabled != ''>disabled="${disabled}"</#if>>
		<@aceicon icon=icon bigger=bigger/>
		<#nested>
	</button>
	</#if>
</#macro>

<#--
 竖向排列form表单里的一行
 inc 是否自动包含input表单元素
 type 表单元素类型，如text，hidden等
 title 行标题
 name 表单元素名称
 value 表单元素值
 widthleft 左侧标题占用12分之几
 widthdata 中间数据占用12分之几，剩余为提示信息宽度
-->
<#macro formgroup  inc=true type='text' title='title' name='name' value='' widthleft=3 widthdata=5>
	<#if inc>
		<@row>
        <div class="form-group">
            <label class="control-label col-xs-${widthleft} col-sm-${widthleft} col-md-${widthleft} col-lg-${widthleft}">${title}：</label>
            <div class="col-xs-${widthdata} col-sm-${widthdata} col-md-${widthdata} col-lg-${widthdata}">
				<#nested>
            </div>
        </div>
		</@row>
	<#else>
		<@row>
        <div class="form-group">
            <label for="id-${name}" class="control-label col-xs-${widthleft} col-sm-${widthleft-1} col-md-${widthleft-1} col-lg-${widthleft-1}">${title}：</label>
            <div class="col-xs-${12-widthleft} col-sm-${12-widthleft+1} col-md-${12-widthleft+1} col-lg-${12-widthleft+1}">
                <input type="${type}" name="${name}" value="${value}" placeholder="${title}" class="form-control" id="id-${name}">
            </div>
        </div>
		</@row>
	</#if>
</#macro>


<#--
 横向排列form表单里的一项
 title 标题
-->
<#macro querygroup title='title' >
<div class="form-group">
    <div class="input-group">
        <div class="input-group-addon">${title}：</div>
		<#nested>
    </div>
</div>
</#macro>


<#--日期控件-->
<#macro date name dateValue=.now?date required="" default=false>
    	<div class="input-group">
    	<#if default>
    		<input name="${name}" class="date form-control date-picker" type="text" check-type="${required}" value="${dateValue?string('yyyy-MM-dd')}" readonly="readonly" placeholder="请点击选择日期"/>
    	<#else>
    		<input name="${name}" class="date form-control date-picker" type="text" check-type="${required}" value="<#if dateValue?string('yyyy-MM-dd')!=.now?date?string('yyyy-MM-dd')>${dateValue?date?string('yyyy-MM-dd')}<#else></#if>" readonly="readonly" placeholder="请点击选择日期"/>
    	</#if>
		<span class="input-group-addon">
			<i class="fa fa-calendar bigger-110"></i>
		</span>
	</div>
</#macro>


<#--swich 控件-->
<#macro swichInForm name val="valid" onVal="valid" offVal="invalid" onTitle="是" offTitle="否">
<label>
	<input type="hidden" name="${name}" value="${val}">
    <input class="ace ace-switch ace-switch-3" type="checkbox" <#if val=onVal>checked="checked"</#if>>
	<span class="lbl"></span>
	<span>(ON:${onTitle}  OFF:${offTitle})</span>
</label>
<script>
$(function() {
	$(".ace-switch").each(function(){
        $(this).click(function(){
        	if($(this).is(":checked"))
        	{
        		$(this).prev().val("${onVal}");
        	}else
        	{
        		$(this).prev().val("${offVal}");
        	}
        });
    });
});
</script>
</#macro>

<#--
下拉列表框
    name                名称
    value               值
    list                集合
    listKey=''          选项内容对应的方法名
    listValue=''        选项的值对应的方法名
    headerKey=''        头选项的显示
    headerValue=''      头选项的值
    emptyOption=false   包含一个空的选项
    id=''               ID
    class=''            样式表
    style=''            样式
    title=''            提示信息
    disabled=false      不可用
    size=-1             显示数量
    tabindex=-1         tab顺序
    onChange            值变化触发事件
    checkType           表单校验类型，遵照bootstrap-validate规则
    sortBy              排序字段
    multi               是否多选
    search              是否显示筛选输入框
-->
<#macro select name list value='' listKey='' listValue='' headerKey='请选择' headerValue='' emptyOption=true id='' class='' style='' title='' disabled=false size=-1 tabindex=-1 onChange='' checkType='' sortBy='' multi=false search=true>
<select <#if multi>multiple="multiple"</#if> name="${name}" <#if id?exists && id!=''>id="${id}"</#if> class="${class}" <#if style?exists && style!=''>style="${style}"</#if> <#if checkType?exists && checkType!=''>check-type="${checkType}"</#if> <#if title?exists && title!=''>title="${title}"</#if> <#if size gt 0> size="${size}"</#if> <#if disabled>disabled="disabled"</#if> <#if tabindex gt 0>tabindex="${tabindex}"</#if> <#if onChange?exists && onChange!=''>onChange='${onChange}'</#if> <#if value?exists>init-data=<@array2str array=value/></#if> >

    <#if emptyOption&&multi=false>
        <option value="${headerValue}">${headerKey}</option>
    </#if>
	
	<#if sortBy?exists&&sortBy!=''>
		<#if listKey?exists && listKey!='' && listValue?exists && listValue!=''>
			<#list list?if_exists?sort_by(sortBy) as item>
				<#assign itemTitle=item[listKey]>
				<#assign itemValue=item[listValue]>
                <option value="${itemValue}" <#if equal(value,itemValue)>selected="selected"</#if> >${itemTitle}</option>
			</#list>
		<#elseif list?is_hash&&!list?is_collection>
			<#list list?if_exists?sort_by(sortBy)?keys as key>
				<#assign itemTitle=key>
				<#assign itemValue=list[key]>
                <option value="${itemValue}" <#if equal(value,itemValue)>selected="selected"</#if> >${itemTitle}</option>
			</#list>
		<#else>
			<#list list?if_exists?sort_by(sortBy) as item>
				<#assign itemTitle=XJJDict.getText(item?string)>
				<#assign itemValue=item>
                <option value="${itemValue}" <#if equal(value,itemValue)>selected="selected"</#if> >${itemTitle}</option>
			</#list>
		</#if>
	<#else>
		<#if listKey?exists && listKey!='' && listValue?exists && listValue!=''>
			<#list list?if_exists as item>
				<#assign itemTitle=item[listKey]>
				<#assign itemValue=item[listValue]>
				<#if extra!=''><#assign itemExtra=item[extra]></#if>
                <option value="${itemValue}" <#if equal(value,itemValue)>selected="selected"</#if> <#if extra!=''>extra='${itemExtra}'</#if>>${itemTitle}</option>
			</#list>
		<#elseif list?is_hash&&!list?is_collection>
			<#list list?if_exists?keys as key>
				<#assign itemTitle=key>
				<#assign itemValue=list[key]>
                <option value="${itemValue}" <#if equal(value,itemValue)>selected="selected"</#if> >${itemTitle}</option>
			</#list>
		<#else>
			<#list list?if_exists as item>
				<#assign itemTitle=XJJDict.getText(item?string)>
				<#assign itemValue=item>
                <option value="${itemValue}" <#if equal(value,itemValue)>selected="selected"</#if> >${itemTitle}</option>
			</#list>
		</#if>
	</#if>
</select>

<script type="text/javascript">
    $(function(){
		var config = {
		  '.chosen-select'           : { no_results_text: '未找到匹配项'},
		  '.chosen-select-deselect'  : { allow_single_deselect: true },
		  '.chosen-select-no-single' : { disable_search_threshold: 10 },
		  '.chosen-select-rtl'       : { rtl: true },
		}
		for (var selector in config) {
		  $(selector).chosen(config[selector]);
		}
	});
</script>
</#macro>

<#--
上传区宏   默认限制5M
fileInfo="" id="default" uploadPath="" datepath="true" callBackFunc="uploadCallBack" format="true" fileId="fileId" multiple='true' buttonText="上传文件"
-->
<#macro upload fileInfo="" id="default" uploadPath="" datepath="true" callBackFunc="uploadCallBack" format="true" fileId="fileId" multiple='true' allowedExtensions="['jpg','jpeg','png','gif','doc','docx','pdf','xls','xlsx','zip','mp3','mp4','wmv','apk']" sizeLimit="5242880" buttonText="上传文件">
<div id="file-uploader-${id}" ></div>
<script type="text/javascript">
    new qq.FileUploader({
        element: document.getElementById('file-uploader-${id}'),
        action: '${base}//sys/fileupload/upload?path=${uploadPath}&datepath=${datepath}&format=${format}',
        deleteUrl:'${base}/components/fileupload/delete/',
		<#if callBackFunc?exists && callBackFunc!="">
            onComplete: function(id, fileName, responseJSON){
				${callBackFunc}(responseJSON.success,responseJSON.fileId);
            },
		</#if>
        multiple:${multiple},
        allowedExtensions:${allowedExtensions},
        sizeLimit:${sizeLimit},
        uploadButtonText:'${buttonText}',
        fileIdInputName:'${fileId}',
		<#if fileInfo?exists && fileInfo!="" && fileInfo.hasFiles>
            files:${fileInfo.fileInfos},
		</#if>
        debug: true
    });
</script>
</#macro>
