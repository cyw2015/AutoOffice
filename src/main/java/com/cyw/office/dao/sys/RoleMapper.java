package com.cyw.office.dao.sys;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.sys.Role;


public interface RoleMapper {

	List<Role> getRolePage(Map<String, Object> paramsMap);

	Role findByRoleCode(String roleCode);

	int insert(Map<String, Object> paramsMap);

	int updateByRoleCode(Map<String, Object> paramsMap);

	int deleteRolesByIds(String[] roleCodes);
	
}