/****************************************************
 * Description: Entity for 资源管理
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      xjj
 * @version     1.0
**************************************************/

package com.xjj.framework.security.dto;

import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Resource{

    private static final long serialVersionUID = 1L;

    public Resource(){}
    public Resource(Long id){
        this.id = id;
    }

    private Long id=new Random().nextLong();
    private Integer type;//类型：1=网页；2=按钮；3=数据
    private String content;//内容：网页则是url

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
     * @return 类型：1=网页；2=按钮；3=数据
     */
    public Integer getType() {
        return type;
    }
    
    /**
     * @param 类型：1=网页；2=按钮；3=数据
     */
    public void setType(Integer type) {
        this.type = type;
    }
    
    /**
     * @return 内容：网页则是url
     */
    public String getContent() {
        return content;
    }
    
    /**
     * @param 内容：网页则是url
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    public int hashCode() {
        return new HashCodeBuilder().append("com.xjj.framework.security.dto.Resource").append(this.id).toHashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        if (!(obj instanceof Resource))
            return false;
        final Resource that = (Resource)obj;
        return new EqualsBuilder().append(this.id, that.id).isEquals();
    }

    public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
				.append("Resource")
				.append(",type="+this.getType())
				.append(",content="+this.getContent())
				.toString();
    }

}

