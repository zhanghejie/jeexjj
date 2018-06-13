package com.xjj.common.backstage.index.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjj.framework.web.SpringControllerSupport;
import com.xjj.sec.entity.MenuEntity;
import com.xjj.sec.entity.RoleEntity;
import com.xjj.sec.service.MenuService;
import com.xjj.sec.service.RoleService;

@Controller
@RequestMapping("/backstage") 
public class BackstageController extends SpringControllerSupport{
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/index")
	public String index(Model model) {
		
		List<MenuEntity> menuList = null;
		
		//如果为超级管理员，则显示全部菜单。
		if("admin".equals(this.getManagerInfo().getLoginName()))
		{
			List<MenuEntity> allMmenuList = menuService.findAllValid();
			List<MenuEntity> treeMenuList =  new ArrayList<MenuEntity>();
			String menuCode =  null;
			MenuEntity rootMenu =  null;
			for (int i = 0; i < allMmenuList.size(); i++) {
				
				menuCode = allMmenuList.get(i).getCode();
				if(menuCode.length()==2)
				{
					treeMenuList.add(allMmenuList.get(i));
				}else if(menuCode.length()==4) {
					rootMenu = treeMenuList.get(treeMenuList.size()-1);
					rootMenu.addSubMenu(allMmenuList.get(i).copy());
				}
			}
			menuList = treeMenuList;
		}else
		{
			List<RoleEntity> roleList = roleService.findListByUserId(this.getManagerInfo().getUserId());
			menuList = menuService.findMenusByRoleIds(getRoleIds(roleList));
		}
		
		
		model.addAttribute("menuList",menuList);
		return this.getViewPath("index");
	}
	
	
	@RequestMapping(value = "/notice")
	public String notice() {
		
		return this.getViewPath("notice");
	}
	
	/**
	 * 菜单
	 * @return
	 */
	@RequestMapping(value = "/menu")
	public String menu() {
		
		return this.getViewPath("menu");
	}
	
	
	private Long[] getRoleIds(List<RoleEntity> roleList)
	{
		if(null == roleList || roleList.size()==0)
		{
			return null;
		}
		
		Long[] roleIds = new Long[roleList.size()];
		for (int i = 0; i < roleList.size(); i++) {
			roleIds[i]=roleList.get(i).getId();
		}
		return roleIds;
	}
	
	
	
}
