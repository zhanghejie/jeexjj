package com.xjj.sys.dict.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.sys.dict.entity.DictItem;
import com.xjj.sys.dict.service.DictService;
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

@Controller
@RequestMapping("/sys/dict")
public class DictController extends SpringControllerSupport{
	@Autowired
	private DictService dictService;

	@SecPrivilege(title="字典管理")
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
		page = dictService.findPage(query,page);
		return getViewPath("list");
	}
	
	@SecCreate
	@RequestMapping("/input")
	public String input(@ModelAttribute(name="dict") DictItem dict,Model model) {
		
		return getViewPath("input");
	}
	
	/*
	 * 修改用户
	 */
	@SecEdit
	@RequestMapping("/input/{id}")
	public String edit(@PathVariable("id") Long id, Model model){
		DictItem dict = dictService.getById(id);
		model.addAttribute("dict",dict);
		return getViewPath("input");
	}
	
	/**
	 * @return
	 */
	@SecCreate
	@SecEdit
	@RequestMapping("/save")
	public @ResponseBody XjjJson  save(@ModelAttribute DictItem dict){
		
		if(dict.isNew())
		{
			dictService.save(dict);
		}else
		{
			dictService.update(dict);
		}
		return XjjJson.success("保存成功");
	}
	
	@SecDelete
	@RequestMapping("/delete/{id}")
	public @ResponseBody XjjJson delete(@PathVariable("id") Long id){
		dictService.delete(id);
		return XjjJson.success("成功删除1条");
	}
	
	@SecDelete
	@RequestMapping("/delete")
	public @ResponseBody XjjJson delete(@RequestParam("ids") Long[] ids){
		if(ids == null || ids.length == 0){
			return XjjJson.error("没有删除");
		}
		for(Long id : ids){
			dictService.delete(id);
		}
		return XjjJson.success("成功删除"+ids.length+"条");
	}
}
