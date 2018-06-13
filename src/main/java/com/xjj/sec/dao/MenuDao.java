/****************************************************
 * Description: DAO for 菜单
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-12 zhanghejie Create File
**************************************************/
package com.xjj.sec.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xjj.framework.dao.XjjDAO;
import com.xjj.sec.entity.MenuEntity;

public interface MenuDao  extends XjjDAO<MenuEntity> {
	
	public List<MenuEntity> findMenusByPid(@Param("pid") Long pid);
}

