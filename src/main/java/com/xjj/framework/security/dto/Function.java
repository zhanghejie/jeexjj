/****************************************************
 * Description: Entity for 功能
 * Copyright:   Copyright (c) 2013
 * Company:     xjj
 * @author      xjj
 * @version     1.0
**************************************************/

package com.xjj.framework.security.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class Function {

    private static final long serialVersionUID = 1L;

    public Function(){}
    public Function(Long id){
        this.id = id;
    }

    private Long id=new Random().nextLong();
    private Privilege privilege;//所属权限
    private String code;//编码
    private String title;//名称
    private String desc;//描述
    private Collection<Resource> resources;//资源管理
    private Collection<Function> depends;//资源管理
    private String[] dependCodes;//资源管理

    /**
     * @return 主键
     */
    public Long getId() {
        return id;
    }
    /**
     * @param 主键
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * 获得所属权限
     * @return
     */
    public Privilege getPrivilege() {
		return privilege;
	}
    /**
     * 设置所属权限
     * @param privilege
     */
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	/**
     * @return 编码
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param 编码
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return 名称
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @param 名称
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return 描述
     */
    public String getDesc() {
        return desc;
    }
    
    /**
     * @param 描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /**
     * @return 资源管理
     */
    public Collection<Resource> getResources() {
        return resources;
    }
	
    /**
     * @param 资源管理
     */
    public void setResources(Collection<Resource> resources) {
        this.resources = resources;
    }
	
    /**
     * @param 资源管理
     */
    public void addResource(Resource resource) {
    	if(this.resources == null){
    		this.resources = new ArrayList<Resource>();
    	}
        this.resources.add(resource);
    }
    /**
     * @param 资源管理
     */
    public void addResource(Collection<Resource> resources) {
    	if(resources == null || resources.size() == 0){
    		return;
    	}
    	if(this.resources == null){
    		this.resources = new ArrayList<Resource>();
    	}
    	this.resources=CollectionUtils.union(this.resources, resources);
    }	
    
    /**
     * @param 资源管理
     */
    public void addURL(String url) {
    	Resource r = new Resource();
    	r.setContent(url);
    	r.setType(1);
    	addResource(r);
    }
    /**
     * @param 资源管理
     */
    public void removeResource(Resource resource) {
    	if(this.resources == null){
    		return;
    	}
        this.resources.remove(resource);
    }

    /**
	 * @return the depends
	 */
	public Collection<Function> getDepends() {
		return depends;
	}
	/**
	 * @param depends the depends to set
	 */
	public void setDepends(Collection<Function> depends) {
		this.depends = depends;
	}
	
	/**
     * @param 资源管理
     */
    public void addDepend(Function function) {
    	if(this.depends == null){
    		this.depends = new ArrayList<Function>();
    	}
        this.depends.add(function);
    }

    /**
     * @param 资源管理
     */
    public void removeDepend(Function function) {
    	if(this.depends == null){
    		return;
    	}
        this.depends.remove(function);
    }
	
	/**
	 * @return the dependCodes
	 */
	public String[] getDependCodes() {
		return dependCodes;
	}
	/**
	 * @param dependCodes the dependCodes to set
	 */
	public void setDependCodes(String[] dependCodes) {
		this.dependCodes = dependCodes;
	}
	
	public int hashCode() {
        return new HashCodeBuilder().append("com.xjj.framework.security.dto.Function").append(this.id).toHashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        if (!(obj instanceof Function))
            return false;
        final Function that = (Function)obj;
        return new EqualsBuilder().append(this.id, that.id).isEquals();
    }

    public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
				.append("Function")
				.append(",code=" + this.getCode())
				.append(",title=" + this.getTitle()).toString();
    }

}

