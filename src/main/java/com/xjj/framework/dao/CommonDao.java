package com.xjj.framework.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonDao {
	/**
	 * 校验是否唯一
	 * @param id 大于0不唯一
	 */
	public int checkUniqueVal(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("columnVal") String columnVal,
			@Param("id") Long id);
}