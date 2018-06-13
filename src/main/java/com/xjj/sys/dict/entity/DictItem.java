package com.xjj.sys.dict.entity;

import com.xjj.framework.entity.EntitySupport;

/**
 * 字典组
 */
public class DictItem extends EntitySupport{

	private long id;
	private String name;
	private String groupCode;
	private String code;
	private String status;
	private String detail;
	
	//序号
	private int sn;

	public DictItem() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	@Override
	public String toString() {
		return "DictItem [id=" + id + ", name=" + name + ", code=" + code
				+ "]";
	}
}
