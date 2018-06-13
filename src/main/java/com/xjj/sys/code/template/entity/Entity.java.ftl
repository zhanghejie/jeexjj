<#macro getterAndSetter propertyName propertyType docText>
    /**
     * 返回${docText}
     * @return ${docText}
     */
    public ${propertyType?cap_first} <#if propertyType="boolean">is<#else>get</#if>${propertyName?cap_first}() {
        return ${propertyName?uncap_first};
    }
    
    /**
     * 设置${docText}
     * @param ${propertyName?uncap_first} ${docText}
     */
    public void set${propertyName?cap_first}(${propertyType?cap_first} ${propertyName?uncap_first}) {
        this.${propertyName?uncap_first} = ${propertyName?uncap_first};
    }
    
</#macro>  

<#macro getterAndSetterForCollection propertyName propertyType docText>
 	/**
     * 返回${docText}
     * @return ${docText}
     */
    public Collection<${propertyType?cap_first}> get${propertyName?cap_first}s() {
        return ${propertyName?uncap_first}s;
    }
	
    /**
     * 设置${docText}
     * @param ${propertyName?uncap_first} ${docText}
     */
    public void set${propertyName?cap_first}s(Collection<${propertyType?cap_first}> ${propertyName?uncap_first}s) {
        this.${propertyName?uncap_first}s = ${propertyName?uncap_first}s;
    }
	
    /**
     * 设置${docText}
     * @param ${propertyName?uncap_first} ${docText}
     */
    public void add${propertyName?cap_first}(${propertyType?cap_first} ${propertyName?uncap_first}) {
        this.${propertyName?uncap_first}s.add(${propertyName?uncap_first});
    }
	
    /**
     * 设置${docText}
     * @param ${propertyName?uncap_first} ${docText}
     */
    public void remove${propertyName?cap_first}(${propertyType?cap_first} ${propertyName?uncap_first}) {
        this.${propertyName?uncap_first}s.remove(${propertyName?uncap_first});
    }
    
</#macro>  
/****************************************************
 * Description: Entity for ${model.label}
 * Copyright:   Copyright (c) ${model.year}
 * Company:     ${model.company}
 * @author      ${model.author}
 * @version     ${model.version}
 * @see
	HISTORY
    *  ${model.date} ${model.author} Create File
**************************************************/

package ${model.packageForModel};

<#if model.hasDateField>
import java.util.Date;
</#if>
import com.xjj.framework.entity.EntitySupport;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ${model.name?cap_first}Entity extends EntitySupport {

    private static final long serialVersionUID = 1L;
    public ${model.name?cap_first}Entity(){}
<#list model.fields?if_exists as field>
	<#if field.propName!="id">
    private ${field.propType} ${field.propName};//${field.columnComment}
    </#if>
</#list>
<#list model.fields?if_exists as field>
	<#if field.propName!="id">
    <@getterAndSetter propertyName="${field.propName}" propertyType="${field.propType}" docText="${field.columnComment}"/>
	</#if>
</#list>

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("${model.packageForModel}.${model.name?cap_first}Entity").append("ID="+this.getId()).toString();
    }
}

