package com.xjj.sys.code.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.sys.code.GCConfig;
import com.xjj.sys.code.entity.ColumnInfo;
import com.xjj.sys.code.entity.TableInfo;
import com.xjj.sys.code.generator.GCGenerator;
import com.xjj.sys.code.generator.GCModel;
import com.xjj.sys.code.service.CodeService;
import com.xjj.framework.json.XjjJson;
import com.xjj.framework.security.annotations.SecFunction;
import com.xjj.framework.security.annotations.SecPrivilege;
import com.xjj.framework.utils.StringUtils;
import com.xjj.framework.web.SpringControllerSupport;

@Controller
@RequestMapping("/sys/code")
public class CodeController extends SpringControllerSupport{

	@Autowired
	private CodeService codeService;
	
	@SecPrivilege(title="代码生成管理")
	@RequestMapping(value = "/index")
	public String generate(Model model) {
		List<String> tableList = codeService.findTableList();
		model.addAttribute("tableList",tableList);
		
		return getViewPath("index");
	}
	
	
	/**
	 * 代码生成成功
	 * @param tables
	 * @return
	 */
	@SecFunction(code="generate",title="代码生成")
	@RequestMapping("/generate")
	public @ResponseBody
	XjjJson generate(@RequestParam(required=false,value="tables") String[] tables,
			@RequestParam("codePath") String codePath,
			@RequestParam("globalPackage") String globalPackage,
			@RequestParam("projectName") String projectName,
			@RequestParam("diffTable") String diffTable,
			@RequestParam("tablePre") String tablePre,
			@RequestParam("template") String template){
		
		if(tables == null || tables.length == 0){
			return XjjJson.error("请选择要生成代码的数据库表");
		}
		
		//参数初始化
		if(!StringUtils.isBlank(codePath))
		{
			GCConfig.DIR_BASE=codePath;
		}
		
		String tableName = null;
		for (int i = 0; i < tables.length; i++) {
			TableInfo tableInfo = codeService.getTableInfoByName(tables[i]);
			List<ColumnInfo> columnList = codeService.findColumnsByTable(tables[i]);
			tableName = tableInfo.getTableName();
			
			GCModel model = GCModel.initGCModel(diffTable,globalPackage, projectName, template, tableName,tablePre, tableInfo.getTableComment(), columnList);
			
			GCGenerator.generateCode(model);
		}
		return XjjJson.success("保存成功");
	}
	
}
