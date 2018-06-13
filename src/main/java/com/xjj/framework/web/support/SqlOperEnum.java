package com.xjj.framework.web.support;

/**
 * 使用枚举表述常量数据字典
 */
public enum SqlOperEnum {
	EQUAL("eq", "="), LIKE("lk", "like"), LIKE_LEFT("kl", "like"), LIKE_RIGHT("kr", "like"),
	GREAT_THAN("gt", ">"), LESS_THAN("lt", "<"), GREAT_EQUAL("ge", ">="), LESS_EQUAL("le", "<="), 
	IS_NULL("nu", "is null"), NOT_EQUAL("ne", "!="), NOT_NULL("nn", "is not null");

	private String oper;

	private String sql;

	private SqlOperEnum(String oper, String sql) {
		this.oper = oper;
		this.sql = sql;
	}


	public String getOper() {
		return oper;
	}


	public void setOper(String oper) {
		this.oper = oper;
	}



	public String getSql() {
		return sql;
	}



	public void setSql(String sql) {
		this.sql = sql;
	}


//
//	public static SqlOperEnum get(int index) {
//		for (SqlOperEnum state : values()) {
//			if (state.getState() == index) {
//				return state;
//			}
//		}
//		return null;
//	}
//	
	public static void main(String[] args) {
		//enum AAAAA;
	}

}
