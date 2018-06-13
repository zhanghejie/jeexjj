/****************************************************
 * Description: Controller for 用户角色
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
	*  2018-04-18 zhanghejie Create File
**************************************************/
package com.xjj.sec.web;
import com.xjj.sec.entity.UserRoleEntity;
import com.xjj.sec.service.UserRoleService;
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

@Controller
@RequestMapping("/sec/user/role")
public class UserRoleController extends SpringControllerSupport{
	@Autowired
	private UserRoleService userRoleService;
	
	@RequestMapping(value = "/index")
	public String index(Model model) {
		String page = this.getViewPath("index");
		return page;
	}

	@RequestMapping(value = "/list")
	public String list(Model model,
			@QueryParameter XJJParameter query,
			@ModelAttribute("page") Pagination page
			) {
		page = userRoleService.findPage(query,page);
		return getViewPath("list");
	}
	
	
	@RequestMapping("/input")
	public String create(@ModelAttribute("userRole") UserRoleEntity userRole,Model model){
		return getViewPath("input");
	}
	@RequestMapping("/input/{id}")
	public String edit(@PathVariable("id") Long id, Model model){
		UserRoleEntity userRole = userRoleService.getById(id);
		model.addAttribute("userRole",userRole);
		return getViewPath("input");
	}
	
	@RequestMapping("/save")
	public @ResponseBody XjjJson save(@ModelAttribute UserRoleEntity userRole){
		
		validateSave(userRole);
		if(userRole.isNew())
		{
			//userRole.setCreateDate(new Date());
			userRoleService.save(userRole);
		}else
		{
			userRoleService.update(userRole);
		}
		return XjjJson.success("保存成功");
	}
	
	
	/**
	 * 数据校验
	 **/
	private void validateSave(UserRoleEntity userRole){
		//必填项校验
		// 判断USER_ID是否为空
		if(null==userRole.getUserId()){
			throw new ValidationException("校验失败，USER_ID不能为空！");
		}
		// 判断ROLE_ID是否为空
		if(null==userRole.getRoleId()){
			throw new ValidationException("校验失败，ROLE_ID不能为空！");
		}
	}
	
	
	@RequestMapping("/delete/{id}")
	public @ResponseBody XjjJson delete(@PathVariable("id") Long id){
		userRoleService.delete(id);
		return XjjJson.success("成功删除1条");
	}
	
	@RequestMapping("/delete")
	public @ResponseBody XjjJson delete(@RequestParam("ids") Long[] ids){
		if(ids == null || ids.length == 0){
			return XjjJson.error("没有选择删除记录");
		}
		for(Long id : ids){
			userRoleService.delete(id);
		}
		return XjjJson.success("成功删除"+ids.length+"条");
	}
}

