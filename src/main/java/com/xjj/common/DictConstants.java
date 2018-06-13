package com.xjj.common;

import java.util.HashMap;
import java.util.List;

import com.xjj.SpringBeanLoader;
import com.xjj.sys.dict.entity.DictItem;
import com.xjj.sys.dict.service.DictService;


public class DictConstants extends XJJConstants{
	
	//{"provice_shandong":"山东","provice_henan":"河南"}
	private static HashMap<String,String> DICT_CACHE_MAP = new HashMap<String,String>();// 角色权限缓存

	/**
	 * 自定义字典组
	 */
	public static final String DICT_GENDER ="gender";
	public static final String DICT_PROVINCE ="province";
	
	public static final String[] DICT_GROUP = {DICT_GENDER,DICT_PROVINCE};
	
	
	
	/**
	 * 初始化所有的权限信息，重新加载
	 */
	public static void init(){
		reloadFromDB();
	}

	/**
	 * 重新加载所有字典缓存
	 */
	public static void reloadFromDB() {
		
		DICT_CACHE_MAP.clear();
		
		DictService dictService = (DictService) SpringBeanLoader.getBean("dictServiceImpl");
		List<DictItem> dictList = dictService.findAll();
		
		for (int i = 0; i < dictList.size(); i++) {
			DICT_CACHE_MAP.put(dictList.get(i).getGroupCode()+"_"+dictList.get(i).getCode(), dictList.get(i).getName());
		}
	}
	
	/**
	 * 根据组code和字典code获得名称
	 * @param groupCode
	 * @param dictCode
	 */
	public static String getDictName(String groupCode,String dictCode) {
		
		if(DICT_CACHE_MAP.containsKey(groupCode+"_"+dictCode))
		{
			return DICT_CACHE_MAP.get(groupCode+"_"+dictCode);
		}
		return null;
	}
}