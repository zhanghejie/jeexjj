/****************************************************
 * Description: Entity for 用户角色
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/

package com.xjj.sec.entity;

import com.xjj.framework.entity.EntitySupport;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserRoleEntity extends EntitySupport {

    private static final long serialVersionUID = 1L;
    public UserRoleEntity(){}
    private Long userId;//USER_ID
    private Long roleId;//ROLE_ID
    /**
     * 返回USER_ID
     * @return USER_ID
     */
    public Long getUserId() {
        return userId;
    }
    
    /**
     * 设置USER_ID
     * @param userId USER_ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    /**
     * 返回ROLE_ID
     * @return ROLE_ID
     */
    public Long getRoleId() {
        return roleId;
    }
    
    /**
     * 设置ROLE_ID
     * @param roleId ROLE_ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("com.xjj.sec.entity.UserRoleEntity").append("ID="+this.getId()).toString();
    }
}

