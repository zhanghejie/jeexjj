package com.xjj.framework.web.support;

import java.util.HashSet;
import java.util.Set;

import com.xjj.framework.utils.StringUtils;


/**
 * 批量验证信息的消息处理
 */
public class ValidationMessage {

	private String result = "";//验证结果
	private Set<String> messages = new HashSet<String>();//验证信息
	
	/**
	 * 初始化
	 */
	public ValidationMessage(){ }
	
	/**
	 * 初始化，如果给出验证结果，则会自动拼接到导出文本中，例如：<br>
	 * result = “无法删除”<br>
	 * 导出信息为：“由于 ****，****等原因，无法删除”<br>
	 * 否则只返回原因：“****，****”
	 * @param result 验证结果
	 */
	public ValidationMessage(String result){
		this.result = result;
	}
	
	/**
	 * 初始化
	 */
	public static ValidationMessage create(){
		return new ValidationMessage();
	}
	
	/**
	 * 初始化，如果给出验证结果，则会自动拼接到导出文本中，例如：<br>
	 * result = “无法删除”<br>
	 * 导出信息为：“由于 ****，****等原因，无法删除”<br>
	 * 否则只返回原因：“****，****”
	 * @param result 验证结果
	 */
	public static ValidationMessage create(String result){
		return new ValidationMessage(result);
	}
	
	/**
	 * 设置验证结果
	 * @param msgs 验证结果
	 * @return
	 */
	public ValidationMessage setResult(Object... results){
		if(results == null || results.length == 0){
			this.result = "";
			return this;
		}
		if(results.length == 1){
			this.result = results[0].toString();
			return this;
		}
		StringBuilder builder = new StringBuilder();
		for(Object r : results){
			builder.append(r);
		}
		this.result = builder.toString();
		return this;
	}
	
	/**
	 * 添加验证结果信息
	 * @param msgs 验证结果信息
	 * @return
	 */
	public ValidationMessage addMessage(String msg){
		if(!StringUtils.isBlank(msg) && !messages.contains(msg)){
				messages.add(msg);
		}
		return this;
	}
	
	/**
	 * 返回拼接后的字符串<br>
	 * 如果给出验证结果，则会自动拼接到导出文本中，例如：result = “无法删除”<br>
	 * 导出信息为：“由于 ****，****等原因，无法删除”<br>
	 * 否则只返回原因：“****，****”
	 * @return
	 */
	public String toMessage(){
		if(messages == null || messages.size() == 0){
			return "";
		}
		StringBuilder builder = null;
		for(String msg : messages){
			if(builder == null){
				builder = new StringBuilder(StringUtils.isBlank(result)?"":"由于 ");
				builder.append(msg);
			}else{
				builder.append("，").append(msg);
			}
		}
		if(!StringUtils.isBlank(result)){
			builder.append(" 原因，").append(result);
		}
		
		return builder.toString();
	}
}
