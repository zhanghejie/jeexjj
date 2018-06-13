package com.xjj.sys.code.generator;

import java.util.ArrayList;
import java.util.List;

import com.xjj.sys.code.GCConfig;
import com.xjj.sys.code.entity.ColumnInfo;
import com.xjj.framework.utils.DateTimeUtils;
import com.xjj.framework.utils.StringUtils;


/**
 * 类对应每个数据库表
 * @author zhanghejie
 *
 */
public class GCModel {
	
	public static GCModel initGCModel(
			String diffTable,
			String globalPackage,
			String projectName,
			String template,
			String tableName,
			String tablePre,
			String tableComment,
			List<ColumnInfo> columnList
			)
	{
		GCModel gcModel = new GCModel();
		gcModel.setGlobalPackage(globalPackage);
		gcModel.setGlobalLabel(projectName);
		gcModel.setTablePre(tablePre);
		gcModel.setTableName(tableName);
		gcModel.setLabel(tableComment);
		gcModel.setFields(columnList);
		gcModel.setModule();
		gcModel.setDiffTable(diffTable);
		
		//设置类名和访问路径
		gcModel.setName();
		gcModel.setRequestMapping();
		
		return gcModel;
	}
	
	//#############################################################
	//公共属性
	public String getYear(){
		return String.valueOf(DateTimeUtils.getCurrentYear());
	}
	public String getDate(){
		return DateTimeUtils.getCurrentDateString();
	}
	public String getCompany(){
		return GCConfig.getInstance().getCompany();
	}
	public String getAuthor(){
		return GCConfig.getInstance().getAuthor();
	}
	public String getVersion(){
		return GCConfig.getInstance().getVersion();
	}
	public String getUrlPath(){
		return GCConfig.getInstance().getUrlPath();
	}
	//###############################################################
	//设置的属性
	
	private String diffTable;//模块下是否还按照表名来区分目录
	private String globalPackage;//总的路径
	private String globalLabel;//总的中文名
	private String name;//类名称
	private String label;//类的中文名
	//private String show;//显示的名字，默认为第一个字符串的属性
	private String tableName;//数据库表名字
	private String tablePre;//表前缀
	private String requestMapping; //controller的访问地址
	
	private String module;//模块
	
	
	private List<ColumnInfo> fields = new ArrayList<ColumnInfo>();
	
	public String getGlobalPackage() {
		return globalPackage;
	}
	public void setGlobalPackage(String globalPackage) {
		this.globalPackage = globalPackage;
	}
	public String getGlobalLabel() {
		return globalLabel;
	}
	public void setGlobalLabel(String globalLabel) {
		this.globalLabel = globalLabel;
	}
	public String getName() {
		return name;
	}
	public String getCamelName() {
		return StringUtils.toCamelCase(StringUtils.toUnderScoreCase(tableName));
	}
	public void setName() {
		String tName =  tableName.replace(tablePre,"");
		
		int firstUnderline = tName.indexOf("_");
		
		if(firstUnderline>0)
		{
			tName = tName.substring(firstUnderline);
		}
		
		this.name= StringUtils.toCamelCase(StringUtils.toUnderScoreCase(tName),true);
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	public String getDiffTable() {
		return diffTable;
	}

	public void setDiffTable(String diffTable) {
		this.diffTable = diffTable;
	}


	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName.toLowerCase();
	}
	
	public String getTablePre() {
		return tablePre;
	}

	public void setTablePre(String tablePre) {
		this.tablePre = tablePre.toLowerCase();
	}
	
	public String getModule() {
		
		return module;
	}
	
	public void setModule() {
		
		String tName =  tableName.replace(tablePre,"");
		int firstUnderline = tName.indexOf("_");
		
		if(firstUnderline>0)
		{
			this.module = tName.substring(0, firstUnderline);
		}else
		{
			this.module = tableName;
		}
	}

	public String getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping() {
		//模块名
		String modelPath =  getModule();
		this.requestMapping = "/"+modelPath+StringUtils.toURIFromUnderCase(StringUtils.toUnderScoreCase(name));
	}

	public List<ColumnInfo> getFields() {
		return fields;
	}
	public void setFields(List<ColumnInfo> fields) {
		this.fields = fields;
	}
	public void addField(ColumnInfo field) {
		this.fields.add(field);
	}

	//#########################################################################
	//生成需要的属性
	/**
	 * 生成完成的类路径，规则：globlePackage.***.modelPackage，需要检验是否含有modelPackage
	 */
	public String getPackageForModel(){
		return this.genPackage("entity");
	}
	public String getPackageForDAO(){
		return this.genPackage("dao");
	}
	public String getPackageForDAOImpl(){
		return this.genPackage("dao","hibernate");
	}
	public String getPackageForService(){
		return this.genPackage("service");
	}
	public String getPackageForServiceImpl(){
		return this.genPackage("service","impl");
	}
	public String getPackageForAction(){
		return this.genPackage("web");
	}
	public String getPackageXML(){
		return this.genPackage(null,null);
	}
	private String genPackage(String pack){
		return this.genPackage(pack, null);
	}
	private String genPackage(String pack,String sub){
		StringBuffer p = new StringBuffer();
		boolean insert_dot = false;
		if(this.globalPackage != null && !this.globalPackage.equals("")){
			insert_dot = true;
			p.append(this.globalPackage);
		}
		
		if(module != null && !module.equals("")){
			if(insert_dot){
				p.append(".");
			}
			insert_dot = true;
			p.append(module);
		}
		
		//如果模块下区分目录
		if("yes".equals(diffTable))
		{
			if(insert_dot){
				p.append(".");
			}
			insert_dot = true;
			
			//模快下的目录不会再分目录
			String diffPath = StringUtils.toUnderScoreCase(name);
			int _idx = diffPath.indexOf("_");
			if(_idx>0)
			{
				diffPath=diffPath.substring(0,_idx);
			}
			p.append(diffPath);
		}
		
		if(pack != null && !pack.equals("")){
			if(insert_dot){
				p.append(".");
			}
			insert_dot = true;
			p.append(pack);
		}
		
		if(sub != null && !sub.equals("")){
			if(insert_dot){
				p.append(".");
			}
			insert_dot = true;
			p.append(sub);
		}
		return p.toString();
	}
	
	
	/**
	 * 检测是否含有特殊类型的字段
	 */
	public boolean isHasDateField(){
		for(ColumnInfo mf:fields){
			if(mf.getDataType().contains("date")){
				return true;
			}
		}
		return false;
	}
}
