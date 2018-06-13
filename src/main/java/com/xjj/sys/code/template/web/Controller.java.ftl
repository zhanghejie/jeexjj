/****************************************************
 * Description: Controller for ${model.label}
 * Copyright:   Copyright (c) ${model.year}
 * Company:     ${model.company}
 * @author      ${model.author}
 * @version     ${model.version}
 * @see
	HISTORY
	*  ${model.date} ${model.author} Create File
**************************************************/
package ${model.packageForAction};
import ${model.packageForModel}.${model.name?cap_first}Entity;
import ${model.packageForService}.${model.name?cap_first}Service;
<#if model.hasDateField>
import java.util.Date;
</#if>
import com.xjj.framework.json.XjjJson;
import com.xjj.framework.exception.ValidationException;
import com.xjj.framework.web.SpringControllerSupport;
import com.xjj.framework.web.support.Pagination;
import com.xjj.framework.web.support.QueryParameter;
import com.xjj.framework.web.support.XJJParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.framework.security.annotations.SecCreate;
import com.xjj.framework.security.annotations.SecDelete;
import com.xjj.framework.security.annotations.SecEdit;
import com.xjj.framework.security.annotations.SecList;
import com.xjj.framework.security.annotations.SecPrivilege;

@Controller
@RequestMapping("${model.requestMapping}")
public class ${model.name?cap_first}Controller extends SpringControllerSupport{
	@Autowired
	private ${model.name?cap_first}Service ${model.name?uncap_first}Service;
	
	
	@SecPrivilege(title="${model.label}管理")
	@RequestMapping(value = "/index")
	public String index(Model model) {
		String page = this.getViewPath("index");
		return page;
	}
	
	@SecList
	@RequestMapping(value = "/list")
	public String list(Model model,
			@QueryParameter XJJParameter query,
			@ModelAttribute("page") Pagination page
			) {
		page = ${model.name?uncap_first}Service.findPage(query,page);
		return getViewPath("list");
	}
	
	@SecCreate
	@RequestMapping("/input")
	public String create(@ModelAttribute("${model.name?uncap_first}") ${model.name?cap_first}Entity ${model.name?uncap_first},Model model){
		return getViewPath("input");
	}
	
	@SecEdit
	@RequestMapping("/input/{id}")
	public String edit(@PathVariable("id") Long id, Model model){
		${model.name?cap_first}Entity ${model.name?uncap_first} = ${model.name?uncap_first}Service.getById(id);
		model.addAttribute("${model.name?uncap_first}",${model.name?uncap_first});
		return getViewPath("input");
	}
	
	@SecCreate
	@SecEdit
	@RequestMapping("/save")
	public @ResponseBody XjjJson save(@ModelAttribute ${model.name?cap_first}Entity ${model.name?uncap_first}){
		
		validateSave(${model.name?uncap_first});
		if(${model.name?uncap_first}.isNew())
		{
			//${model.name?uncap_first}.setCreateDate(new Date());
			${model.name?uncap_first}Service.save(${model.name?uncap_first});
		}else
		{
			${model.name?uncap_first}Service.update(${model.name?uncap_first});
		}
		return XjjJson.success("保存成功");
	}
	
	
	/**
	 * 数据校验
	 **/
	private void validateSave(${model.name?cap_first}Entity ${model.name?uncap_first}){
		//必填项校验
		<#list model.fields as field>
		<#if field.required && field.propName!='id'>
			<#if field.propType=='text'||field.propType=='string'>
		// 判断${field.columnComment}是否为空
		if(StringUtils.isBlank(${model.name?uncap_first}.get${field.propName?cap_first}())){
			throw new ValidationException("校验失败，${field.columnComment}不能为空！");
		}
			<#else>
		// 判断${field.columnComment}是否为空
		if(null==${model.name?uncap_first}.get${field.propName?cap_first}()){
			throw new ValidationException("校验失败，${field.columnComment}不能为空！");
		}
			</#if>
		</#if>
		</#list>
	}
	
	@SecDelete
	@RequestMapping("/delete/{id}")
	public @ResponseBody XjjJson delete(@PathVariable("id") Long id){
		${model.name?uncap_first}Service.delete(id);
		return XjjJson.success("成功删除1条");
	}
	@SecDelete
	@RequestMapping("/delete")
	public @ResponseBody XjjJson delete(@RequestParam("ids") Long[] ids){
		if(ids == null || ids.length == 0){
			return XjjJson.error("没有选择删除记录");
		}
		for(Long id : ids){
			${model.name?uncap_first}Service.delete(id);
		}
		return XjjJson.success("成功删除"+ids.length+"条");
	}
}

