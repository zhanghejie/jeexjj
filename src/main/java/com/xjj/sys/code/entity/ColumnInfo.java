package com.xjj.sys.code.entity;

import com.xjj.framework.utils.StringUtils;


public class ColumnInfo{
	
	//数据库属性
	private String columnName;
	private String dataType;
	private String columnComment;
	private String isNullable;//是否可为空
	
	
	
	//对应类属性
	private boolean search = false;
	private boolean required= false;
	private String propName;
	private String propType;
	//private String checkType;

	public ColumnInfo() {
		
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnComment() {
		if(StringUtils.isBlank(columnComment))
		{
			return columnName;
		}
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	
	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public boolean isRequired() {
		if("no".equalsIgnoreCase(isNullable))
		{
			required = true ;
		}
		
		return required;
	}

	public String getPropName() {
		propName = StringUtils.toCamelCase(columnName);
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropType() {
		
		propType = "String";
		
		if(dataType.toLowerCase().contains("bigint"))
		{
			propType=  "Long";
		}
		
		if(dataType.toLowerCase().contains("int"))
		{
			propType=  "Integer";
		}
		
		if (dataType.toLowerCase().contains("varchar")
				|| dataType.toLowerCase().contains("text"))
		{
			propType=  "String";
		}
		
		if(dataType.toLowerCase().contains("date"))
		{
			propType=  "Date";
		}
		if(dataType.toLowerCase().contains("float"))
		{
			propType=  "Float";
		}
		if(dataType.toLowerCase().contains("double"))
		{
			propType= "Double";
		}
		if(dataType.toLowerCase().contains("decimal"))
		{
			propType= "BigDecimal";
		}
		
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}
	

	public String getCheckType() {
		String checkType = "";
		if(isRequired())
		{
			checkType+="required";
		}
		
		
		if("Long".equals(getPropType()) || "Integer".equals(getPropType()))
		{
			if(!StringUtils.isBlank(checkType))
			{
				checkType+=" ";
			}
			checkType+="number";
		}
		
		return checkType;
	}

	@Override
	public String toString() {
		return new StringBuffer(getClass().getName()).append(":columnName=").append(getColumnName()).toString();
	}
}