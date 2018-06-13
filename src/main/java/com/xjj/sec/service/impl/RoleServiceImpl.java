/****************************************************
 * Description: ServiceImpl for 角色
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/

package com.xjj.sec.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjj.framework.dao.XjjDAO;
import com.xjj.framework.security.PrivilegeService;
import com.xjj.framework.security.dto.Function;
import com.xjj.framework.security.dto.Privilege;
import com.xjj.framework.security.dto.TreeNode;
import com.xjj.framework.security.interceptor.PermissionRole.Role;
import com.xjj.framework.service.XjjServiceSupport;
import com.xjj.framework.utils.StringUtils;
import com.xjj.sec.dao.RoleDao;
import com.xjj.sec.dao.RolePrivilegeDao;
import com.xjj.sec.entity.RoleEntity;
import com.xjj.sec.entity.RolePrivilegeEntity;
import com.xjj.sec.service.RoleService;

@Service
public class RoleServiceImpl extends XjjServiceSupport<RoleEntity> implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public XjjDAO<RoleEntity> getDao() {
		
		return roleDao;
	}
	
	public List<RoleEntity> findListNoUser(Long userId)
	{
		return roleDao.findListNoUser(userId);
	}
	
	public RoleEntity getByCode(String code)
	{
		return roleDao.getByCode(code);
	}
	
	public List<RolePrivilegeEntity> findPrivilegeByRole(Long roleId)
	{
		return roleDao.findPrivilegeByRole(roleId);
	}
	
	
	public List<RoleEntity> findListByUserId(Long userId)
	{
		return roleDao.findListByUserId(userId);
	}
}