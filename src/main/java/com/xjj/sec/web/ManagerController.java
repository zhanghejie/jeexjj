package com.xjj.sec.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.common.XJJConstants;
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
import com.xjj.sec.entity.UserRoleEntity;
import com.xjj.sec.entity.XjjUser;
import com.xjj.sec.service.RoleService;
import com.xjj.sec.service.UserRoleService;
import com.xjj.sec.service.UserService;

@Controller
@RequestMapping("/sec/manager")
public class ManagerController extends SpringControllerSupport{

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@SecPrivilege(title="管理员管理")
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
		//只查询类型为管理员的用户
		query.addQuery("query.userType@eq@s",XJJConstants.USER_TYPE_ADMIN);
		query.addOrderByAsc("id");
		page = userService.findPage(query,page);
		return getViewPath("list");
	}
	
	@SecCreate
	@RequestMapping("/input")
	public String input(@ModelAttribute("user") XjjUser user,Model model) {
		
		return getViewPath("input");
	}
	
	/*
	 * 修改用户
	 */
	@SecEdit
	@RequestMapping("/input/{id}")
	public String edit(@PathVariable("id") Long id, Model model){
		XjjUser user = userService.getById(id);
		model.addAttribute("user",user);
		return getViewPath("input");
	}
	
	
	
	/*
	 * 去添加角色
	 */
	@RequestMapping("/role/input/{userId}")
	public String role(@PathVariable("userId") Long userId, Model model){
		XjjUser user = userService.getById(userId);
		List<RoleEntity> roleList = roleService.findListNoUser(userId);
		model.addAttribute("roleList",roleList);
		model.addAttribute("user",user);
		return getViewPath("role");
	}
	
	
	@RequestMapping("/role/save")
	public @ResponseBody XjjJson  roleSave(
			@RequestParam(value="userId") Long userId,
			@RequestParam(value="roleIds") Long[] roleIds){
		
		if (null != roleIds) {
			for (int i = 0; i < roleIds.length; i++) {
				UserRoleEntity userRole = new UserRoleEntity();
				userRole.setUserId(userId);
				userRole.setRoleId(roleIds[i]);
				userRoleService.save(userRole);
			}
		}
		return XjjJson.success("保存成功");
	}
	
	@RequestMapping("/role/cancle/{userId}/{roleId}")
	public @ResponseBody XjjJson cancleRole(@PathVariable("userId") Long userId,
			@PathVariable("roleId") Long roleId){
		userRoleService.deleteBy2Id(userId,roleId);
		return XjjJson.success("成功删除1条");
	}
	
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@RequestMapping("/save")
	public @ResponseBody XjjJson  save(@ModelAttribute XjjUser user){
		
		if(user.isNew())
		{
			user.setCreateDate(new Date());
			user.setUserType(XJJConstants.USER_TYPE_ADMIN);
			userService.save(user);
		}else
		{
			userService.update(user);
		}
		return XjjJson.success("保存成功");
	}
	

	@RequestMapping(value = "/{userId}/detail", method = RequestMethod.POST)
	public String detail(@PathVariable("userId") Long userId, Model model) {
		if (userId == null) {
			return "redirect:/user/list";
		}
		XjjUser user = userService.getById(userId);
		if (user == null) {
			return "forward:/user/list";
		}
		model.addAttribute("user", user);
		return "detail";
	}
	
	@SecDelete
	@RequestMapping("/delete/{id}")
	public @ResponseBody XjjJson delete(@PathVariable("id") Long id){
		userService.delete(id);
		return XjjJson.success("成功删除1条");
	}
	
	@SecDelete
	@RequestMapping("/delete")
	public @ResponseBody XjjJson delete(@RequestParam("ids") Long[] ids){
		if(ids == null || ids.length == 0){
			return XjjJson.error("没有删除");
		}
		for(Long id : ids){
			userService.delete(id);
		}
		return XjjJson.success("成功删除"+ids.length+"条");
	}
	
}
