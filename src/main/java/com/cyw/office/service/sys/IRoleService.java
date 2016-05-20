package com.cyw.office.service.sys;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.sys.Role;

public interface IRoleService {

	List<Role> getRolePage(Map<String, Object> paramsMap);

	Role findByRoleCode(String roleCode);

	int insert(Map<String, Object> paramsMap);

	int updateByRoleCode(Map<String, Object> paramsMap);

	int deleteRolesByIds(String[] roleCodes);

	int configAuth(List<Map<String, Object>> list);

	List<Role> getConfigRole(String userCode);

}
