/****************************************************
 * Description: ServiceImpl for 用户角色
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/

package com.xjj.sec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xjj.framework.dao.XjjDAO;
import com.xjj.framework.service.XjjServiceSupport;
import com.xjj.sec.entity.UserRoleEntity;
import com.xjj.sec.dao.UserRoleDao;
import com.xjj.sec.service.UserRoleService;

@Service
public class UserRoleServiceImpl extends XjjServiceSupport<UserRoleEntity> implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public XjjDAO<UserRoleEntity> getDao() {
		
		return userRoleDao;
	}
	
	public void deleteBy2Id(Long userId,Long roleId)
	{
		userRoleDao.deleteBy2Id(userId,roleId);
	}
}