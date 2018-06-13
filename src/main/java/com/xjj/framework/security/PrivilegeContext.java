package com.xjj.framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.xjj.framework.security.dto.Function;
import com.xjj.framework.security.dto.Privilege;

public class PrivilegeContext {
	private Map<String, Privilege> privilegeMap = new HashMap<String, Privilege>();
	
	public Privilege getPrivilege(String code){
		if(code == null){
			return null;
		}
		return privilegeMap.get(code);
	}
	
	public Collection<Privilege> getPrivileges(){
		return privilegeMap.values();
	}
	
	public boolean addPrivilege(Privilege privilege){
		if(privilege == null || privilege.getCode() == null){
			return false;
		}
		if(privilegeMap.containsKey(privilege.getCode())){
			return false;
		}
		privilegeMap.put(privilege.getCode(), privilege);
		return true;
	}
	
	public boolean addFunction(Function function){
		if(function == null || function.getPrivilege() == null || function.getPrivilege().getCode() == null){
			return false;
		}
		Privilege privilege = getPrivilege(function.getPrivilege().getCode());
		if(privilege == null){
			return false;
		}
		Function f = privilege.getFunction(function.getCode());
		if(f == null){
			if(privilege.getFunctions()==null){
				privilege.setFunctions(new ArrayList());
			}
			privilege.getFunctions().add(function);
		}else{
			f.addResource(function.getResources());
		}
		return true;
	}
}
