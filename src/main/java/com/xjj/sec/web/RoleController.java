/****************************************************
 * Description: Controller for 角色
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
	*  2018-04-18 zhanghejie Create File
**************************************************/
package com.xjj.sec.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.framework.json.XjjJson;
import com.xjj.framework.security.annotations.SecCreate;
import com.xjj.framework.security.annotations.SecDelete;
import com.xjj.framework.security.annotations.SecEdit;
import com.xjj.framework.security.annotations.SecList;
import com.xjj.framework.security.annotations.SecPrivilege;
import com.xjj.framework.web.SpringControllerSupport;
import com.xjj.framework.web.support.Pagination;
import com.xjj.framework.web.support.QueryParameter;
import com.xjj.framework.web.support.XJJParameter;
import com.xjj.sec.entity.RoleEntity;
import com.xjj.sec.service.RoleService;

@Controller
@RequestMapping("/sec/role")
public class RoleController extends SpringControllerSupport{
	@Autowired
	private RoleService roleService;
	
	@SecPrivilege(title="角色管理")
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
		page = roleService.findPage(query,page);
		return getViewPath("list");
	}
	
	@SecCreate
	@RequestMapping("/input")
	public String create(@ModelAttribute("role") RoleEntity role,Model model){
		return getViewPath("input");
	}
	
	@SecEdit
	@RequestMapping("/input/{id}")
	public String edit(@PathVariable("id") Long id, Model model){
		RoleEntity role = roleService.getById(id);
		model.addAttribute("role",role);
		return getViewPath("input");
	}
	
	@RequestMapping("/save")
	public @ResponseBody XjjJson save(@ModelAttribute RoleEntity role){
		
		validateSave(role);
		if(role.isNew())
		{
			//role.setCreateDate(new Date());
			roleService.save(role);
		}else
		{
			roleService.update(role);
		}
		return XjjJson.success("保存成功");
	}
	
	
	/**
	 * 数据校验
	 **/
	private void validateSave(RoleEntity role){
		//必填项校验
	}
	
	@SecDelete
	@RequestMapping("/delete")
	public @ResponseBody XjjJson delete(@RequestParam("ids") Long[] ids){
		if(ids == null || ids.length == 0){
			return XjjJson.error("没有选择删除记录");
		}
		for(Long id : ids){
			roleService.delete(id);
		}
		return XjjJson.success("成功删除"+ids.length+"条");
	}
}

