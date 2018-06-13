/****************************************************
 * Description: Controller for 菜单
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
	*  2018-04-12 zhanghejie Create File
**************************************************/
package com.xjj.sec.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.common.XJJConstants;
import com.xjj.framework.exception.ValidationException;
import com.xjj.framework.json.XjjJson;
import com.xjj.framework.security.PrivilegeService;
import com.xjj.framework.security.annotations.SecCreate;
import com.xjj.framework.security.annotations.SecDelete;
import com.xjj.framework.security.annotations.SecEdit;
import com.xjj.framework.security.annotations.SecList;
import com.xjj.framework.security.annotations.SecPrivilege;
import com.xjj.framework.security.dto.Privilege;
import com.xjj.framework.utils.StringUtils;
import com.xjj.framework.web.SpringControllerSupport;
import com.xjj.framework.web.support.QueryParameter;
import com.xjj.framework.web.support.XJJParameter;
import com.xjj.sec.entity.MenuEntity;
import com.xjj.sec.service.MenuService;

@Controller
@RequestMapping("/sec/menu")
public class MenuController extends SpringControllerSupport{
	@Autowired
	private MenuService menuService;
	
	
	@SecPrivilege(title="菜单管理")
	@RequestMapping(value = "/index")
	public String index(Model model) {
		String page = this.getViewPath("index");
		return page;
	}

	@SecList
	@RequestMapping(value = "/list")
	public String list(Model model,
			@QueryParameter XJJParameter query
			) {
		
		//查询第一级菜单
		List<MenuEntity> menuList = menuService.findMenusByPid(null);
		MenuEntity menu = null;
		for (int i = 0; i < menuList.size(); i++) {
			
			menu = menuList.get(i);
			//查询子菜单
			menu.setSubMenus(menuService.findMenusByPid(menu.getId()));
		}
		
		model.addAttribute("menuList",menuList);
		return getViewPath("list");
	}
	
	/**
	 * 增加菜单
	 * @param pid 父菜单，0为根菜单
	 * @param menu
	 * @param model
	 * @return
	 */
	
	@SecCreate
	@RequestMapping("/{pid}/input")
	public String create(
			@PathVariable("pid") Long pid,
			@ModelAttribute("menu") MenuEntity menu,
			Model model){
		
		if(pid.intValue()!=0)
		{
			MenuEntity parentMenu = menuService.getById(pid);
			model.addAttribute("parentMenu",parentMenu);
		}
		model.addAttribute("privileges", PrivilegeService.getPrivileges());
		return getViewPath("input");
	}
	
	@SecEdit
	@RequestMapping("/input/{id}")
	public String edit(@PathVariable("id") Long id, Model model){
		MenuEntity menu = menuService.getById(id);
		model.addAttribute("menu",menu);
		
		if(null!= menu.getParentId() && menu.getParentId().intValue()>0)
		{
			MenuEntity parentMenu = menuService.getById(menu.getParentId());
			model.addAttribute("parentMenu",parentMenu);
		}
		model.addAttribute("privileges", PrivilegeService.getPrivileges());
		return getViewPath("input");
	}
	
	
	
	@SecCreate
	@SecEdit
	@RequestMapping("/save")
	public @ResponseBody XjjJson save(@ModelAttribute MenuEntity menu){
		
		validateSave(menu);
		menu.setUrl(this.getPrivilegeURL(menu.getPrivilegeCode()));
		if(menu.isNew())
		{
			menuService.save(menu);
		}else
		{
			menuService.update(menu);
		}
		return XjjJson.success("保存成功");
	}
	
	
	/**
	 * 数据校验
	 **/
	private void validateSave(MenuEntity menu){
		//必填项校验
		// 判断TITLE是否为空
		if(null==menu.getTitle()){
			throw new ValidationException("请输入名称");
		}
		// 判断status是否为空
		if(null==menu.getStatus()){
			throw new ValidationException("请选择状态");
		}
		
		if(null==menu.getCode())
		{
			throw new ValidationException("请输入编码");
		}
		
		String parentCode = this.getRequest().getParameter("parentCode");
		if(StringUtils.isBlank(parentCode))
		{
			if(menu.getCode().length()!=2)
			{
				throw new ValidationException("请输入两位的编码");
			}
		}else
		{
			if(!menu.getCode().startsWith(parentCode))
			{
				throw new ValidationException("前"+parentCode.length()+"位请与父菜单编码保持一致");
			}
			
			if(menu.getCode().length()!=parentCode.length()+2)
			{
				throw new ValidationException("请输入"+(parentCode.length()+2)+"位的编码");
			}
		}
	}
	
	@SecDelete
	@RequestMapping("/delete/{id}")
	public @ResponseBody XjjJson delete(@PathVariable("id") Long id){
		
		try {
			menuService.delete(id);
		} catch (Exception e) {
			return XjjJson.error("请删空子菜单后，再删除父菜单");
		}
		return XjjJson.success("成功删除1条");
	}
	
	@RequestMapping("/disable/{id}")
	public @ResponseBody XjjJson disable(@PathVariable("id") Long id){
		MenuEntity menu = menuService.getById(id);
		menu.setStatus(XJJConstants.COMMON_STATUS_INVALID);
		menuService.update(menu);
		return XjjJson.success("禁用菜单成功！");
	}
	
	
	@RequestMapping("/enable/{id}")
	public @ResponseBody XjjJson enable(@PathVariable("id") Long id){
		MenuEntity menu = menuService.getById(id);
		menu.setStatus(XJJConstants.COMMON_STATUS_VALID);
		menuService.update(menu);
		return XjjJson.success("启用菜单成功！");
	}
	
	/*
	 * 通过权限code得到相应的URL
	 * @param code
	 * @return
	 */
	private String getPrivilegeURL(String code){
		
		if(StringUtils.isBlank(code))
		{
			return null;
		}
		Privilege p = PrivilegeService.getPrivilege(code);
		if(null == p)
		{
			return null;
		}
		else{
			return p.getUrl();
		}
	}
}

