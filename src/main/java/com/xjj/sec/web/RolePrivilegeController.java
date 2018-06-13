/****************************************************
 * Description: Controller for t_sec_role_privilege
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
	*  2018-04-18 zhanghejie Create File
**************************************************/
package com.xjj.sec.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.framework.json.XjjJson;
import com.xjj.framework.security.PrivilegeService;
import com.xjj.framework.security.annotations.SecFunction;
import com.xjj.framework.security.dto.Privilege;
import com.xjj.framework.security.dto.TreeNode;
import com.xjj.framework.utils.StringUtils;
import com.xjj.framework.web.SpringControllerSupport;
import com.xjj.framework.web.support.Pagination;
import com.xjj.framework.web.support.QueryParameter;
import com.xjj.framework.web.support.XJJParameter;
import com.xjj.sec.entity.RolePrivilegeEntity;
import com.xjj.sec.service.RolePrivilegeService;

@Controller
@RequestMapping("/sec/role/privilege")
public class RolePrivilegeController extends SpringControllerSupport{
	@Autowired
	private RolePrivilegeService rolePrivilegeService;
	
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
		page = rolePrivilegeService.findPage(query,page);
		return getViewPath("list");
	}
	
	
	@RequestMapping("/input")
	public String create(@ModelAttribute("rolePrivilege") RolePrivilegeEntity rolePrivilege,Model model){
		return getViewPath("input");
	}
	@RequestMapping("/input/{id}")
	public String edit(@PathVariable("id") Long id, Model model){
		RolePrivilegeEntity rolePrivilege = rolePrivilegeService.getById(id);
		model.addAttribute("rolePrivilege",rolePrivilege);
		return getViewPath("input");
	}
	
	@RequestMapping("/save")
	public @ResponseBody XjjJson save(@ModelAttribute RolePrivilegeEntity rolePrivilege){
		
		validateSave(rolePrivilege);
		if(rolePrivilege.isNew())
		{
			rolePrivilegeService.save(rolePrivilege);
		}else
		{
			rolePrivilegeService.update(rolePrivilege);
		}
		return XjjJson.success("保存成功");
	}
	
	
	/**
	 * 数据校验
	 **/
	private void validateSave(RolePrivilegeEntity rolePrivilege){
		//必填项校验
	}
	
	
	@RequestMapping("/delete/{id}")
	public @ResponseBody XjjJson delete(@PathVariable("id") Long id){
		rolePrivilegeService.delete(id);
		return XjjJson.success("成功删除1条");
	}
	
	@RequestMapping("/delete")
	public @ResponseBody XjjJson delete(@RequestParam("ids") Long[] ids){
		if(ids == null || ids.length == 0){
			return XjjJson.error("没有选择删除记录");
		}
		for(Long id : ids){
			rolePrivilegeService.delete(id);
		}
		return XjjJson.success("成功删除"+ids.length+"条");
	}
	
	
	
	//====================================设置权限开始===============================================
	@SecFunction(title="设置权限",code="allot")
	@RequestMapping("/allot/{roleId}")
	public String listpri(@PathVariable("roleId") Long roleId, Model model){
		List<TreeNode> treeNodeList = rolePrivilegeService.listpri(roleId);
		model.addAttribute("roleId",roleId);
		model.addAttribute(treeNodeList);
		return getViewPath("allot");
	}
		
	
	/**
	 * 添加权限
	 * @param roleId
	 * @param priCode
	 * @param functions  多个用|分开
	 * @param model
	 * @return
	 */
	@RequestMapping("/add/{roleId}/{priCode}/{functions}")
	public @ResponseBody XjjJson addPri(@PathVariable("roleId") Long roleId, 
			@PathVariable("priCode") String priCode,
			@PathVariable("functions") String functions,
			Model model){
		
		
		priCode = priCode.replace("menu_","");
		XJJParameter param = new XJJParameter();
		param.addQuery("query.roleId@eq@l", roleId);
		param.addQuery("query.privilegeCode@eq@s", priCode);
		RolePrivilegeEntity rpe = rolePrivilegeService.getByParam(param);
		
		Privilege pri = PrivilegeService.getPrivilege(priCode);
		String priTitle = null;
		if(null!= pri)
		{
			priTitle = PrivilegeService.getPrivilege(priCode).getTitle();
		}
		if(null==rpe)
		{
			rpe = new RolePrivilegeEntity();
			rpe.setRoleId(roleId);
			rpe.setPrivilegeCode(priCode);
			rpe.setFunctionList(functions);
			rpe.setPrivilegeTitle(priTitle);
			rolePrivilegeService.save(rpe);
		}else{
			String[] funcArr = functions.split("\\|");
			if(null != funcArr && funcArr.length>0)
			{
				for (int i = 0; i < funcArr.length; i++) {
					rpe.addFunction(funcArr[i]);
				}
				rpe.setPrivilegeTitle(priTitle);
			}
			rolePrivilegeService.update(rpe);
		}
		return XjjJson.success("为【"+priTitle+"】添加("+functions+")权限成功");
	}
	
	
	/**
	 * 添加权限
	 * @param roleId
	 * @param priCode
	 * @param functions  多个用|分开
	 * @param model
	 * @return
	 */
	@RequestMapping("/cancle/{roleId}/{priCode}/{functions}")
	public @ResponseBody XjjJson cancelPri(@PathVariable("roleId") Long roleId, 
			@PathVariable("priCode") String priCode,
			@PathVariable("functions") String functions,
			Model model){
		priCode = priCode.replace("menu_","");
		Privilege pri = PrivilegeService.getPrivilege(priCode);
		String priTitle = null;
		if(null!= pri)
		{
			priTitle = PrivilegeService.getPrivilege(priCode).getTitle();
		}
		
		XJJParameter param = new XJJParameter();
		param.addQuery("query.roleId@eq@l", roleId);
		param.addQuery("query.privilegeCode@eq@s", priCode);
		RolePrivilegeEntity rpe = rolePrivilegeService.getByParam(param);
		
		if(null!=rpe)
		{
			
			String[] funcArr = functions.split("\\|");
			if(null != funcArr && funcArr.length>0)
			{
				for (int i = 0; i < funcArr.length; i++) {
					rpe.removeFunction(funcArr[i]);
				}
			}
			
			if(StringUtils.isBlank(rpe.getFunctionList()))
			{
				rolePrivilegeService.delete(rpe);
			}else
			{
				rolePrivilegeService.update(rpe);
			}
		}
		return XjjJson.success("为【"+priTitle+"】取消("+functions+")权限成功");
	}
	
	
	public static void main(String[] args) {
		String functions = "edit|delete";
		String[] funcArr = functions.split("\\|");
		if(null != funcArr && funcArr.length>0)
		{
			for (int i = 0; i < funcArr.length; i++) {
				System.out.println(funcArr[i]);
			}
		}
		
	}
}