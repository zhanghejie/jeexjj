/****************************************************
 * Description: ServiceImpl for t_sec_role_privilege
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/

package com.xjj.sec.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjj.framework.dao.XjjDAO;
import com.xjj.framework.security.PrivilegeService;
import com.xjj.framework.security.dto.Function;
import com.xjj.framework.security.dto.Privilege;
import com.xjj.framework.security.dto.TreeNode;
import com.xjj.framework.service.XjjServiceSupport;
import com.xjj.framework.utils.StringUtils;
import com.xjj.framework.web.support.XJJParameter;
import com.xjj.sec.dao.RoleDao;
import com.xjj.sec.dao.RolePrivilegeDao;
import com.xjj.sec.entity.RoleEntity;
import com.xjj.sec.entity.RolePrivilegeEntity;
import com.xjj.sec.service.RolePrivilegeService;

@Service
public class RolePrivilegeServiceImpl extends XjjServiceSupport<RolePrivilegeEntity> implements RolePrivilegeService {

	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public XjjDAO<RolePrivilegeEntity> getDao() {
		
		return rolePrivilegeDao;
	}
	public RolePrivilegeEntity getByParam(XJJParameter param)
	{
		List<RolePrivilegeEntity> list = rolePrivilegeDao.findList(param.getQueryMap());
		
		if(list.size()==1)
		{
			return list.get(0);
		}
		return null;
	}
	
	public List<TreeNode> listpri(Long roleid) {
		// 1.得到角色
		RoleEntity role = roleDao.getById(roleid);

		// root
		List<TreeNode> list = new ArrayList<TreeNode>();
		TreeNode root = new TreeNode();
		root.setText(role.getTitle());
		root.setState("open");
		root.setId("rootNode");
		root.setNodes(ptoTree(findPrivileges(), roleid));

		list.add(root);

		return list;
	}

	private Collection<Privilege> findPrivileges() {
		return PrivilegeService.getPrivileges();
	}

	private List<TreeNode> ptoTree(Collection<Privilege> privileges, Long roleid) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		if (privileges == null)
			return list;

		TreeNode node;
		for (Privilege p : privileges) {
			node = new TreeNode();
			node.setText(p.getTitle());
			node.setId(p.getCode());
			Map<String,Object> map = ftoTree(p.getCode(), roleid);
			node.setNodes((List<TreeNode>) map.get("list"));
			node.setChecked(map.get("flag")==null?false:((Boolean)map.get("flag")));
			list.add(node);
		}

		return list;
	}

	private Map<String,Object> ftoTree(String pcode, Long roleid) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<TreeNode> list = new ArrayList<TreeNode>();
		
		Privilege privilege = PrivilegeService.getPrivilege(pcode);
		if (privilege == null)
			return map;
		Collection<Function> functions = privilege.getFunctions();
		if (functions == null)
			return map;
		List<String> funs = new ArrayList<String>();
		RolePrivilegeEntity rolePrivilege = rolePrivilegeDao.getByRolePri(roleid,
				pcode);
		if (rolePrivilege != null)
			funs = rolePrivilege.getFunctions();

		TreeNode node;
		Integer num = 0;
		for (Function f : functions) {
				if(StringUtils.isBlank(f.getTitle())){
					continue;
				}
				node = new TreeNode();
				node.setText(f.getTitle());
				node.setId(f.getCode());
				if (checkFun(f, funs)){
					node.setChecked(true);
					num++;
				}
				list.add(node);
		}
		if(num != 0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("list", list);
		return map;
	}

	private boolean checkFun(Function f, List<String> funs) {
		if (f.getCode().equals("default"))
			return true;
		for (String s : funs) {
			if (s.equals(f.getCode()))
				return true;
		}

		return false;
	}
}