package com.xjj.framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.xjj.SpringBeanLoader;
import com.xjj.framework.configuration.ConfigUtils;
import com.xjj.framework.security.dto.Function;
import com.xjj.framework.security.dto.Privilege;
import com.xjj.framework.security.dto.Resource;
import com.xjj.framework.utils.StringUtils;
import com.xjj.sec.entity.MenuEntity;
import com.xjj.sec.entity.RoleEntity;
import com.xjj.sec.entity.RolePrivilegeEntity;
import com.xjj.sec.service.MenuService;
import com.xjj.sec.service.RoleService;

/**
 * 权限上下文单例类(作废)
 * @author xjj
 */
public class SecurityContext {
	private static SecurityContext securityContext = new SecurityContext();

	private Map<String, List<MenuEntity>> roleMenus = new HashMap<String, List<MenuEntity>>();// 角色菜单缓存
	private Map<String, List<Privilege>> rolePrivileges = new HashMap<String, List<Privilege>>();// 角色权限缓存
	private Map<String, List<Function>> roleFunctions = new HashMap<String, List<Function>>();// 角色功能缓存
	private Map<String, List<Resource>> roleResources = new HashMap<String, List<Resource>>();// 角色资源缓存
	private List<Resource> allResources = new ArrayList<Resource>();// 角色资源缓存

	
	@Autowired
	private RoleService roleService;
	
	private SecurityContext() {
	}
	private static SecurityContext getInstance() {
		return securityContext;
	}
	/**
	 * 初始化所有的权限信息，重新加载
	 */
	public static void init(){
		reloadFromDB();
	}

	/**
	 * 重新加载所有权限内容
	 */
	public static void reloadFromDB() {
		// 加载角色资源，同时会加载角色功能、角色权限
		reloadRolePrivilegesFromDB();
		System.out.println("加载角色资源--完成");
		reloadRoleMenusFromDB();
		System.out.println("加载角色菜单--完成");
		//缓存所有资源
		Collection<Privilege> privileges=PrivilegeService.getPrivileges();
		if(privileges!=null&&privileges.size()>0){
			for(Privilege p :privileges){
				if(p.getFunctions()==null||p.getFunctions().size()==0) continue;
				for(Function f :p.getFunctions()){
					if(f.getResources()==null||f.getResources().size()==0) continue;
					getInstance().allResources.addAll(f.getResources());
				}
			}
		}
		
	}
	/**
	 * 重新加载全部角色菜单缓存
	 */
	public static void reloadRoleMenusFromDB() {

		// 加载所有菜单

		Map<String, List<Privilege>> rolePrivileges = getInstance().rolePrivileges;

		for (String roleCode : rolePrivileges.keySet()) {
			// 遍历所有角色，分别加载角色菜单
			reloadRoleMenusFromDB(roleCode);
		}
	}

	/**
	 * 重新加载单个角色菜单缓存
	 */
	public static void reloadRoleMenusFromDB(String roleCode) {

		// 加载所有菜单
		MenuService menuService = (MenuService) SpringBeanLoader.getBean("menuServiceImpl");
		
		List<MenuEntity> menus = menuService.findAll();
		Map<String, List<Privilege>> rolePrivileges = getInstance().rolePrivileges;
		if (menus == null || menus.size() == 0 || rolePrivileges == null
				|| rolePrivileges.size() == 0)
			return;
		// 遍历所有菜单，判断角色是否拥有该菜单的权限，需要递归判断
		List<MenuEntity> rMenus = new ArrayList<MenuEntity>();
		for (MenuEntity menu : menus) {
			if (hasMenu(menu, roleCode)) {
				rMenus.add(menu);
			}
		}

		getInstance().roleMenus.put(roleCode, rMenus);
		System.out.println("加载角色菜单--" + roleCode + "--" + rMenus.size());
	}
	
	/**
	 * 递归判断是否拥有菜单权限
	 * 
	 * @param menu
	 * @param roleCode
	 * @return
	 */
	private static boolean hasMenu(MenuEntity menu, String roleCode) {
		List<Privilege> privileges = getInstance().rolePrivileges.get(roleCode);
		if (privileges == null || privileges.size() == 0 || menu == null) {
			return false;
		}
		if (StringUtils.isBlank(menu.getPrivilegeCode())) {
			// 菜单对应权限code为空，需要判断子节点是否有权限
			// 判断是否有子节点
			if (menu.getSubMenus() == null || menu.getSubMenus().size() == 0) {
				// 没有子节点，返回false
				return false;
			} else {
				// 存在子节点，判断子节点是否有权限
				for (MenuEntity m : menu.getSubMenus()) {
					if (hasMenu(m, roleCode)) {
						// 子节点有权限，返回ture
						return true;
					}
				}
				// 子节点没权限，返回false
				return false;
			}
		} else {
			// 菜单对应权限code不为空，判断是否拥有权限
			Privilege p = PrivilegeService
					.getPrivilege(menu.getPrivilegeCode());
			if (p!=null&&privileges!=null&&privileges.size()>0&&privileges.contains(p)) {
				// 父菜单如果有权限，返回ture
				return true;
			} else {
				// 父菜单如果没权限，判断子节点是否有权限
				// 判断是否有子节点
				if (menu.getSubMenus() == null
						|| menu.getSubMenus().size() == 0) {
					// 没有子节点，返回false
					return false;
				} else {
					// 存在子节点，判断子节点是否有权限
					for (MenuEntity m : menu.getSubMenus()) {
						if (hasMenu(m, roleCode)) {
							// 子节点有权限，返回ture
							return true;
						}
					}
					// 子节点没权限，返回false
					return false;
				}
			}

		}
	}

	/**
	 * 判断用户是否拥有菜单权限
	 * 
	 * @param menu
	 * @param roleCodes
	 * @return
	 */
	public static boolean hasMenu(MenuEntity menu, String[] roleCodes) {

		if (menu == null || roleCodes == null||roleCodes.length==0)
			return false;

		for (String roleCode :roleCodes) {
			if (hasMenu(menu, roleCode)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 重新加载所有角色权限数据，包括角色权限、角色功能、角色资源
	 */
	public static void reloadRolePrivilegesFromDB() {
		// 加载角色权限缓存
		RoleService roleService = (RoleService) SpringBeanLoader.getBean("roleServiceImpl");
		if (roleService == null) {
			return;
		}
		List<RoleEntity> roles = roleService.findAll();
		if (roles == null || roles.size() == 0) {
			return;
		}
		// 循环加载角色权限数据
		for (RoleEntity role : roles) {
			reloadRolePrivilegesFromDB(role.getCode());
		}

	}

	/**
	 * 重新加载单个角色权限数据，包括角色权限、角色功能、角色资源
	 * @param roleCode 角色CODE
	 */
	public static void reloadRolePrivilegesFromDB(String roleCode) {
		// 获取角色信息
		RoleService roleService = (RoleService) SpringBeanLoader.getBean("roleServiceImpl");
		if (roleService == null) {
			return;
		}
		RoleEntity role = roleService.getByCode(roleCode);
		if (role == null) {
			return;
		}
		// 获取数据库角色权限信息
		Collection<RolePrivilegeEntity> privileges = roleService.findPrivilegeByRole(role.getId());
		if (privileges == null || privileges.size() == 0) {
			return;
		}
		// 将权限信息放入缓存
		List<Privilege> rolePrivileges = new ArrayList<Privilege>();// 角色权限
		List<Function> functions = new ArrayList<Function>();// 角色功能
		List<Resource> resources = new ArrayList<Resource>();// 角色资源
		for (RolePrivilegeEntity p : privileges) {
			rolePrivileges.add(PrivilegeService.getPrivilege(p
					.getPrivilegeCode()));
			Privilege privilege = PrivilegeService.getPrivilege(p
					.getPrivilegeCode());
			// 获得角色权限下的功能
			if (privilege != null && privilege.getFunctions() != null) {
				
				for (Function fun : privilege.getFunctions()) {
					if (fun == null||p==null||p.getFunctionList()==null||!p.getFunctionList().contains(fun.getCode()))
						continue;
					functions.add(fun);
					Collection<Resource> functionResources = fun.getResources();
					if (functionResources != null
							&& functionResources.size() > 0) {
						resources.addAll(functionResources);
					}
					//处理依赖
					Collection<Function> dependsFunctions=fun.getDepends();
					if(dependsFunctions==null||dependsFunctions.size()==0) continue;
					functions.addAll(dependsFunctions);
					for(Function dependFun:dependsFunctions){
						if(dependFun==null||dependFun.getResources()==null||dependFun.getResources().size()==0)continue;
						resources.addAll(dependFun.getResources());
					}
				}
			}

		}
		getInstance().rolePrivileges.put(role.getCode(), rolePrivileges);
		getInstance().roleFunctions.put(roleCode, functions);
		getInstance().roleResources.put(roleCode, resources);
		
		System.out.println("加载角色资源--" + roleCode + "--" + resources.size());
		System.out.println("加载角色权限--" + role.getCode() + "--"
				+ rolePrivileges.size());
		System.out.println("加载角色功能--" + roleCode + "--" + functions.size());

	}

	/**
	 * 根据用户角色获取用户菜单
	 * 
	 * @param roleCodes
	 * @return
	 */
	public static Set<MenuEntity> getRolesMenu(String[] roleCodes) {
		if (roleCodes == null || roleCodes.length == 0) {
			return null;
		}
		Set<MenuEntity> set = new HashSet<MenuEntity>();
		// 遍历角色，将角色对应菜单放入不重复集合中
		for (String roleCode : roleCodes) {
			List<MenuEntity> menus = getInstance().roleMenus.get(roleCode);
			if (menus != null && menus.size() != 0) {
				set.addAll(menus);
			}
		}
		return set;
	}

	/**
	 * 获取一组角色对应权限
	 * 
	 * @param roleCodes
	 * @return
	 */
	public static Set<Privilege> getRolesPrivilege(String[] roleCodes) {
		if (roleCodes == null || roleCodes.length == 0) {
			return null;
		}
		Set<Privilege> set = new HashSet<Privilege>();
		// 遍历角色，将角色对应菜单放入不重复集合中
		for (String roleCode : roleCodes) {
			List<Privilege> privileges = getInstance().rolePrivileges
					.get(roleCode);
			if (privileges != null && privileges.size() != 0) {
				set.addAll(privileges);
			}
		}
		return set;
	}

	/**
	 * 获取一组角色对应资源
	 * 
	 * @param rolCodes
	 * @return
	 */
	public static Set<Resource> getRolesResource(String[] roleCodes) {
		if (roleCodes == null || roleCodes.length == 0) {
			return null;
		}
		Set<Resource> set = new HashSet<Resource>();
		// 遍历角色，将角色对应菜单放入不重复集合中
		for (String roleCode : roleCodes) {
			List<Resource> resources = getInstance().roleResources
					.get(roleCode);
			if (resources != null && resources.size() != 0) {
				for (Resource r : resources) {
					set.add(r);
				}
			}
		}
		return set;
	}

	/**
	 * 获取一组角色功能
	 * 
	 * @param roleCodes
	 * @return
	 */
	public static Set<Function> getRolesFunction(String[] roleCodes) {
		if (roleCodes == null || roleCodes.length == 0) {
			return null;
		}
		Set<Function> set = new HashSet<Function>();
		// 遍历角色，将角色对应菜单放入不重复集合中
		for (String roleCode : roleCodes) {
			List<Function> functions = getInstance().roleFunctions
					.get(roleCode);
			if (functions != null && functions.size() != 0) {
				set.addAll(functions);
			}
		}
		return set;
	}




	/**
	 * 判断一组角色是否拥有功能访问权限
	 * 
	 * @param roleCodes
	 * @param functionCode
	 * @return
	 */
	public static boolean hasFunction(String[] roleCodes, String privilegeCode,
			String functionCode) {
		if (roleCodes == null || roleCodes.length == 0
				|| StringUtils.isBlank(privilegeCode)
				|| StringUtils.isBlank(functionCode)) {
			return false;
		}
		Set<Function> functions = getRolesFunction(roleCodes);
		if (functions == null || functions.size() == 0) {
			return false;
		}
		for (Function r : functions) {
			if(r==null||r.getPrivilege()==null||r.getPrivilege().getCode()==null) continue;
			if (privilegeCode.equals(r.getPrivilege().getCode())
					&& r.getCode().equals(functionCode)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断用户是否拥有URL访问权限(重载)
	 * 
	 * @param userId
	 * @param url
	 * @return
	 */
	public static boolean hasResource(String[] roleCodes,
			String url) {
		
		if (roleCodes == null || roleCodes.length == 0 ) {
			return false;
		}
		Set<Resource> resources = getRolesResource(roleCodes);
		if (resources == null || resources.size() == 0) {
			return false;
		}
		Boolean isInitiative=ConfigUtils.getBoolean("sec.IS_INITIATIVE", false);
		if(isInitiative){//主动拦截模式，所有url都要验证，没有权限不能访问
			for (Resource r : resources) {
				// 验证URL是否合法
				UrlPathHelper urlPathHelper = new UrlPathHelper();
				//String url = urlPathHelper.getLookupPathForRequest(request);
				AntPathMatcher matcher = new AntPathMatcher();
				if (matcher.match(r.getContent(), url)) {
					return true;
				}
			}
			return false;
		}else{//被动拦截模式，只验证配了权限控制的url，默认可以访问
			List<Resource> resourcesAll = getInstance().allResources;
			if(resourcesAll==null||resourcesAll.size()==0){
				return true;
			}
			UrlPathHelper urlPathHelper = new UrlPathHelper();
			//String url = urlPathHelper.getLookupPathForRequest(request);
			AntPathMatcher matcher = new AntPathMatcher();
			boolean underControll=false;//是否配置了权限控制
			for (Resource r : resourcesAll) {
				// 验证URL是否合法
				if (matcher.match(r.getContent(), url)) {
					underControll=true;//配置了权限
				}
			}
			if(underControll){
				//配置了权限，进一步验证用角色是否可以访问
				for (Resource r1 : resources) {
					// 验证URL是否合法
					if (matcher.match(r1.getContent(), url)) {
						return true;
					}
				}
				return false;
			}else{
				return true;
			}
		}
		
		
	}

	/**
	 * 判断用户是否拥有URL访问权限
	 * 
	 * @param userId
	 * @param url
	 * @return
	 */
	public static boolean hasResource(String[] roleCodes,
			HttpServletRequest request) {
		
		if (roleCodes == null || roleCodes.length == 0 || request == null) {
			return false;
		}
		Set<Resource> resources = getRolesResource(roleCodes);
		if (resources == null || resources.size() == 0) {
			return false;
		}
		Boolean isInitiative=ConfigUtils.getBoolean("sec.IS_INITIATIVE", false);
		if(isInitiative){//主动拦截模式，所有url都要验证，没有权限不能访问
			for (Resource r : resources) {
				// 验证URL是否合法
				UrlPathHelper urlPathHelper = new UrlPathHelper();
				String url = urlPathHelper.getLookupPathForRequest(request);
				AntPathMatcher matcher = new AntPathMatcher();
				if (matcher.match(r.getContent(), url)) {
					return true;
				}
			}
			return false;
		}else{//被动拦截模式，只验证配了权限控制的url，默认可以访问
			List<Resource> resourcesAll = getInstance().allResources;
			if(resourcesAll==null||resourcesAll.size()==0){
				return true;
			}
			UrlPathHelper urlPathHelper = new UrlPathHelper();
			String url = urlPathHelper.getLookupPathForRequest(request);
			AntPathMatcher matcher = new AntPathMatcher();
			boolean underControll=false;//是否配置了权限控制
			for (Resource r : resourcesAll) {
				// 验证URL是否合法
				if (r.getContent()!=null&&matcher.match(r.getContent(), url)) {
					underControll=true;//配置了权限
				}
			}
			if(underControll){
				//配置了权限，进一步验证用角色是否可以访问
				for (Resource r1 : resources) {
					// 验证URL是否合法
					if (matcher.match(r1.getContent(), url)) {
						return true;
					}
				}
				return false;
			}else{
				return true;
			}
		}
		
		
	}
	/**
	 * 判断用户是否拥有访问权限
	 * 
	 * @param roleCodes 角色编码
	 * @param privilegeCode 权限编码
	 * @return
	 */
	public static boolean hasPrivilege(String[] roleCodes,
			String privilegeCode) {
		
		if (roleCodes == null || roleCodes.length == 0 ||StringUtils.isBlank(privilegeCode)) {
			return false;
		}
		Set<Privilege> privileges =getRolesPrivilege(roleCodes);
		if(privileges==null||privileges.size()==0){
			return false;
		}
		for(Privilege privilege : privileges){
			if(privilege==null||StringUtils.isBlank(privilege.getCode())) continue;
			if(privilegeCode.equals(privilege.getCode())){
				return true;
			}
		}
		return false;
	}


}
