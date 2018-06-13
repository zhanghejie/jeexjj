/****************************************************
 * Description: Service for 用户角色
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/
package com.xjj.sec.service;
import com.xjj.sec.entity.UserRoleEntity;
import com.xjj.framework.service.XjjService;

public interface UserRoleService  extends XjjService<UserRoleEntity>{
	
	public void deleteBy2Id(Long userId,Long roleId);
}
