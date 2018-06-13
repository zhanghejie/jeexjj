package com.xjj.framework.entity;



public class EntitySupport{
	
	public Long id;
	
	public boolean isNew() {
		return null==id || id.longValue()<=0;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 指示其他某个对象是否与此对象“相等”
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if (this == obj){
			return true;
		}
		if (!getClass().equals(obj.getClass())){
			return false;
		}
		
		EntitySupport that = (EntitySupport)obj;
		if(that.getId()==null || !getId().equals(that.getId())){
			return false;
		}
		
		return true;
	}

	/**
	 * 返回该对象的哈希码值
	 */
	@Override
	public int hashCode() {
		
		int result = 17;
		
		result = result * 31 + (getClass().getName().hashCode());
		result = result * 31 + (getId()==null?0:getId().hashCode());
		
		return result;
	}

	/**
	 * 返回该对象的字符串表示(类似数组的toString方法输出结果)
	 */
	@Override
	public String toString() {
		return new StringBuffer(getClass().getName()).append(":ID=").append(getId()==null?"NULL":getId()).toString();
	}
}
