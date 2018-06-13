package com.xjj.framework.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xjj.SpringBeanLoader;
import com.xjj.common.XJJConstants;
import com.xjj.framework.utils.StringUtils;
import com.xjj.framework.web.ManagerInfo;
import com.xjj.framework.web.support.XJJParameter;
import com.xjj.sec.entity.RolePrivilegeEntity;
import com.xjj.sec.service.RolePrivilegeService;


/**
 * 权限缓存
 * @author xjj
 */
public class SecurityCache {
	//private static SecurityCache securityCache = new SecurityCache();

	//eg:{1:{"sec_user":"list|create","sys_xfile":""}}
	private static HashMap<Long, HashMap<String,HashSet<String>>> privilegeMap = new HashMap<Long, HashMap<String,HashSet<String>>>();// 角色权限缓存
	
	private SecurityCache() {
	}
	public static HashMap<Long, HashMap<String,HashSet<String>>> getPrivilegeMap() {
		return privilegeMap;
	}
	/**
	 * 初始化所有的权限信息，重新加载
	 */
	public static void init(){
		reloadFromDB(null);
	}

	/**
	 * 重新加载所有权限内容
	 * @param roleId  传空则重新加载全部角色
	 */
	public static void reloadFromDB(Long roleId) {
		
		RolePrivilegeService rolePrivilegeService = (RolePrivilegeService) SpringBeanLoader.getBean("rolePrivilegeServiceImpl");
		
		List<RolePrivilegeEntity> rolePriList = null;
		if(roleId==null || roleId.intValue()==0)
		{
			privilegeMap.clear();
			rolePriList = rolePrivilegeService.findAll();
		}else
		{
			privilegeMap.remove(roleId);
			//查询某角色的
			XJJParameter query =  new XJJParameter();
			query.addQuery("query.roleId@eq@l",roleId);
			rolePriList = rolePrivilegeService.findList(query);
		}
		
		Long roId =  null;
		String priCode =  null;
		String functions = null;
		for (int i = 0; i < rolePriList.size(); i++) {
			roId = rolePriList.get(i).getRoleId();
			priCode = rolePriList.get(i).getPrivilegeCode();
			functions = rolePriList.get(i).getFunctionList();
			
			if(!privilegeMap.containsKey(roId))
			{
				HashMap<String,HashSet<String>> priMap = new HashMap<String,HashSet<String>>();
				privilegeMap.put(roId, priMap);
			}
			if(!StringUtils.isBlank(functions))
			{
				HashSet<String> funSet = new HashSet<String>();
				funSet.addAll(Arrays.asList(functions.split("\\|")));
				privilegeMap.get(roId).put(priCode,funSet);
			}
		}
	}
	
	/**
	 * 验证是否有该权限
	 * @param request
	 * @param priCode
	 * @param function
	 * @return
	 */
	public static boolean hasPrivilege(freemarker.ext.servlet.HttpRequestHashModel request,String priCode,String function) {
		
		if(null == request || null==request.getRequest() || null==request.getRequest().getSession())
		{
			return false;
		}
		
		ManagerInfo manager = (ManagerInfo)request.getRequest().getSession().getAttribute(XJJConstants.SESSION_MANAGER_INFO_KEY);
		
		if(null == manager)
		{
			return false;
		}
		
		if("admin".equals(manager.getLoginName()))
		{
			return true;
		}
		Long[] roleIds = manager.getRoleIds();
		if(null ==roleIds || StringUtils.isBlank(priCode) || StringUtils.isBlank(function))
		{
			return false;
		}
		
		for (int i = 0; i < roleIds.length; i++) {
			if(privilegeMap.containsKey(roleIds[i]) && privilegeMap.get(roleIds[i]).containsKey(priCode))
			{
				if(privilegeMap.get(roleIds[i]).get(priCode).contains(function))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		System.out.println("aa|bb|cc".split("\\|")[0]);
	}
	
}