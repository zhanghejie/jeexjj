package com.xjj.framework.json;

import java.io.Serializable;
import java.util.List;

import com.xjj.common.XJJConstants;
import com.xjj.framework.utils.StringUtils;
/**
 * Ajax返回消息类，Json格式
 * @author zhanghejie
 */
public class XjjJson implements Serializable{

	private static final long serialVersionUID = 1L;

	public static enum MessageType {success,info,warning,error};
	
	private MessageType type = MessageType.success;
	private String message = "";
	private Object item;
	private List<?> items;
	
	public XjjJson() {
		super();
	}
	/**
	 * @param type 类型
	 * @param result 结果
	 * @param message 内容
	 */
	public XjjJson(MessageType type, Object... message) {
		super();
		this.setType(type);
		this.setMessage(message);
	}
	/**
	 * @return 消息类型
	 */
	public String getType() {
		switch(type){
			case success:
			return XJJConstants.MSG_TYPE_SUCCESS;
			case info:
				return XJJConstants.MSG_TYPE_INFO;
			case warning:
				return XJJConstants.MSG_TYPE_WARNING;
			case error:
				return XJJConstants.MSG_TYPE_ERROR;
		}
		return XJJConstants.MSG_TYPE_SUCCESS;
	}
	/**
	 * 设置消息类型
	 * @param 消息类型
	 */
	public void setType(MessageType type) {
		this.type = type;
	}
	/**
	 * @return 消息内容
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 设置消息内容
	 * @param 消息内容
	 */
	public void setMessage(Object... message) {
		this.message = StringUtils.join(message);
	}
	
	/**
	 * 返回附加信息
	 * @return 附加信息
	 */
	public Object getItem() {
		return item;
	}
	/**
	 * 设置附加信息
	 * @param item 附加信息
	 */
	public void setItem(Object item) {
		this.item = item;
	}
	/**
	 * 返回附加信息集合
	 * @return 附加信息集合
	 */
	public List<?> getItems() {
		return items;
	}
	/**
	 * 设置附加信息集合
	 * @param items 附加信息集合
	 */
	public void setItems(List<?> items) {
		this.items = items;
	}
	
	/**
	 * 创建消息
	 * @param type 消息类型
	 * @param message 消息内容
	 * @return 消息
	 */
	public static XjjJson message(MessageType type, Object... message){
		return new XjjJson(MessageType.success, message);
	}
	
	/**
	 * 创建消息，消息类型：information，
	 * @param message 消息内容
	 * @return 消息
	 */
	public static XjjJson success(Object... message){
		return new XjjJson(MessageType.success, message);
	}
	
	/**
	 * 创建消息，消息类型：information，
	 * @param message 消息内容
	 * @return 消息
	 */
	public static XjjJson info(Object... message){
		return new XjjJson(MessageType.info, message);
	}
	/**
	 * 创建消息，消息类型：warning，
	 * @param message 消息内容
	 * @return 消息
	 */
	public static XjjJson warning(Object... message){
		return new XjjJson(MessageType.warning, message);
	}

	/**
	 * 创建消息，消息类型：error
	 * @param message 消息内容
	 * @return 消息
	 */
	public static XjjJson error(Object... message){
		return new XjjJson(MessageType.error, message);
	}
}