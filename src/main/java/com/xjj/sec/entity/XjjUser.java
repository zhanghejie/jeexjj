package com.xjj.sec.entity;

import java.util.Date;
import java.util.List;

import com.xjj.common.XJJConstants;
import com.xjj.framework.entity.EntitySupport;
import com.xjj.framework.utils.StringUtils;

public class XjjUser extends EntitySupport{

	private String loginName;
	private String password;
	private String userName;
	private String gender;
	private String mobile;
	private String email;
	private String province;
	private String address;
	
	private String userType;
	private Date createDate;
	private Date birthday;
	private String status;
	List<RoleEntity> roles;

	public XjjUser() {
	}
	
	public XjjUser(long id, String loginName, String password, String userName,
			String mobile, String email, String address, String userType,
			Date createDate, String status) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.password = password;
		this.userName = userName;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.userType = userType;
		this.createDate = createDate;
		this.status = status;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		
		if(StringUtils.isBlank(password))
		{
			return XJJConstants.USER_INIT_PASSWORD;
		}
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		
		if(StringUtils.isBlank(status))
		{
			return XJJConstants.COMMON_STATUS_VALID;
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}
}