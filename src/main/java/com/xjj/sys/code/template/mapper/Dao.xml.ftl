<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${model.packageForDAO}.${model.name?cap_first}Dao">
	<select id="getById" resultType="${model.name?cap_first}Entity" parameterType="long">
		SELECT
			*
		FROM
			${model.tableName}
		WHERE
			id = ${'#'}{id}
	</select>
	
	<select id="findAll" resultType="${model.name?cap_first}Entity">
		SELECT * FROM ${model.tableName}
	</select>
	
	<insert id="save" useGeneratedKeys="true" keyProperty="id" keyColumn="id" parameterType="${model.packageForModel}.${model.name?cap_first}Entity">
		insert into ${model.tableName}(<#list model.fields as field>${field.columnName}<#if field_index+1!=model.fields?size>,</#if></#list>)
    	values(<#list model.fields as field>${'#'}{${field.propName}}<#if field_index+1!=model.fields?size>,</#if></#list>)
	</insert>
	
	<update id="update" parameterType="${model.name?cap_first}Entity">  
        UPDATE ${model.tableName} SET 
            <#list model.fields as field>
             ${field.columnName} = ${'#'}{${field.propName}}<#if field_index+1!=model.fields?size>,</#if>
            </#list>
        WHERE id = ${'#'}{id};     
	</update>
		
	<delete id="delete" parameterType="long">  
        DELETE FROM ${model.tableName} WHERE id = ${'#'}{id}  
	</delete> 
	
	<select id="getCount" resultType="java.lang.Integer">         
    	select count(id) from ${model.tableName} 
    	<include refid="com.xjj.framework.dao.CommonDao.queryParam"/>
	</select> 
	
	<select id="findList" resultType="${model.name?cap_first}Entity">
		SELECT * FROM ${model.tableName}
		<include refid="com.xjj.framework.dao.CommonDao.queryParam"/>
		<include refid="com.xjj.framework.dao.CommonDao.queryOrder"/>
	</select>
	<select id="findPage" resultType="${model.name?cap_first}Entity">
		SELECT
			*
		FROM
			${model.tableName}
		<include refid="com.xjj.framework.dao.CommonDao.queryParam"/>
		<include refid="com.xjj.framework.dao.CommonDao.queryOrder"/>
		LIMIT ${'#'}{offset}, ${'#'}{limit}
	</select>
	
	<select id="findListByColumnValues" resultType="${model.name?cap_first}Entity">
		SELECT * FROM ${model.tableName}
		where ${'$'}{column} in
        <foreach collection="valArr" index="index" item="item" open="(" separator="," close=")">
              ${'#'}{item}       
        </foreach>    
	</select>
</mapper>