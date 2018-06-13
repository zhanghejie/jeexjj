/****************************************************
 * Description: Service for 菜单
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-12 zhanghejie Create File
**************************************************/
package com.xjj.sec.service;
import java.util.List;

import com.xjj.framework.service.XjjService;
import com.xjj.sec.entity.MenuEntity;

public interface MenuService  extends XjjService<MenuEntity>{
	

	public List<MenuEntity> findMenusByPid(Long pid);
	public List<MenuEntity> findAllValid();
	
	/**
	 * 根据用户角色获得用户的菜单
	 * @param roleIds
	 * @return
	 */
	public List<MenuEntity> findMenusByRoleIds(Long[] roleIds);
}
