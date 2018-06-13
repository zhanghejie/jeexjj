package com.xjj.framework.security.dto;

import com.xjj.sec.entity.RoleEntity;

public class RoleDTO {
	private Integer isDefault;//是否默认 0是默认 1是可选 2是已勾选
	private RoleEntity role;//角色
	

	public RoleDTO() {
		super();
	}
	public RoleDTO(Integer isDefault, RoleEntity role) {
		super();
		this.isDefault = isDefault;
		this.role = role;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public RoleEntity getRole() {
		return role;
	}
	public void setRole(RoleEntity role) {
		this.role = role;
	}
}
