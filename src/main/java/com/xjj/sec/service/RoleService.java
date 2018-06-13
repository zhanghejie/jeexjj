/****************************************************
 * Description: Service for 角色
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/
package com.xjj.sec.service;
import java.util.List;

import com.xjj.framework.service.XjjService;
import com.xjj.sec.entity.RoleEntity;
import com.xjj.sec.entity.RolePrivilegeEntity;

public interface RoleService  extends XjjService<RoleEntity>{
	public List<RoleEntity> findListNoUser(Long userId);
	public RoleEntity getByCode(String code);
	public List<RolePrivilegeEntity> findPrivilegeByRole(Long roleId);
	public List<RoleEntity> findListByUserId(Long userId);
}
