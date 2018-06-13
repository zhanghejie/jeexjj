package com.xjj.sys.code.entity;

import com.xjj.framework.utils.StringUtils;


public class TableInfo{
	
	private String tableName;
	private String tableComment;

	public TableInfo() {
		
	}
	
	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	public String getTableComment() {
		
		if(StringUtils.isBlank(tableComment))
		{
			return tableName;
		}
		return tableComment;
	}



	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}


	@Override
	public String toString() {
		return new StringBuffer(getClass().getName()).append(":tableName=").append(getTableName()).toString();
	}
}