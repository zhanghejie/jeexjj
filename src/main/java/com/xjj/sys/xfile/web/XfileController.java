/****************************************************
 * Description: Controller for t_sys_xfile
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
	*  2018-05-04 zhanghejie Create File
**************************************************/
package com.xjj.sys.xfile.web;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.framework.exception.ValidationException;
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
import com.xjj.sys.xfile.entity.XfileEntity;
import com.xjj.sys.xfile.service.XfileService;

@Controller
@RequestMapping("/sys/xfile")
public class XfileController extends SpringControllerSupport{
	@Autowired
	private XfileService xfileService;
	
	
	@SecPrivilege(title="文件管理")
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
		page = xfileService.findPage(query,page);
		return getViewPath("list");
	}
	
	@SecCreate
	@RequestMapping("/input")
	public String create(@ModelAttribute("xfile") XfileEntity xfile,Model model){
		return getViewPath("input");
	}
	
	@SecEdit
	@RequestMapping("/input/{id}")
	public String edit(@PathVariable("id") Long id, Model model){
		XfileEntity xfile = xfileService.getById(id);
		model.addAttribute("xfile",xfile);
		return getViewPath("input");
	}
	
	@SecCreate
	@SecEdit
	@RequestMapping("/save")
	public @ResponseBody XjjJson save(@ModelAttribute XfileEntity xfile){
		
		validateSave(xfile);
		if(xfile.isNew())
		{
			xfile.setCreateDate(new Date());
			xfileService.save(xfile);
		}else
		{
			xfileService.update(xfile);
		}
		return XjjJson.success("保存成功");
	}
	
	
	/**
	 * 数据校验
	 **/
	private void validateSave(XfileEntity xfile){
		//必填项校验
		// 判断file_realname是否为空
		if(null==xfile.getFileRealname()){
			throw new ValidationException("校验失败，file_realname不能为空！");
		}
		// 判断file_path是否为空
		if(null==xfile.getFilePath()){
			throw new ValidationException("校验失败，file_path不能为空！");
		}
		// 判断file_title是否为空
		if(null==xfile.getFileTitle()){
			throw new ValidationException("校验失败，file_title不能为空！");
		}
		// 判断create_date是否为空
		if(null==xfile.getCreateDate()){
			throw new ValidationException("校验失败，create_date不能为空！");
		}
	}
	
	@SecDelete
	@RequestMapping("/delete/{id}")
	public @ResponseBody XjjJson delete(@PathVariable("id") Long id){
		xfileService.delete(id);
		return XjjJson.success("成功删除1条");
	}
	@SecDelete
	@RequestMapping("/delete")
	public @ResponseBody XjjJson delete(@RequestParam("ids") Long[] ids){
		if(ids == null || ids.length == 0){
			return XjjJson.error("没有选择删除记录");
		}
		for(Long id : ids){
			xfileService.delete(id);
		}
		return XjjJson.success("成功删除"+ids.length+"条");
	}
}

