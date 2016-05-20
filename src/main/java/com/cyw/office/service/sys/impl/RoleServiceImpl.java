package com.cyw.office.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.sys.RoleMapper;
import com.cyw.office.entity.sys.Role;
import com.cyw.office.service.sys.IRoleService;
@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	public List<Role> getRolePage(Map<String, Object> paramsMap) {
		return roleMapper.getRolePage(paramsMap);
	}

	public Role findByRoleCode(String roleCode) {
		return this.roleMapper.findByRoleCode(roleCode);
	}

	public int insert(Map<String, Object> paramsMap) {
		return this.roleMapper.insert(paramsMap);
	}

	public int updateByRoleCode(Map<String, Object> paramsMap) {
		return this.roleMapper.updateByRoleCode(paramsMap);
	}

	public int deleteRolesByIds(String[] roleCodes) {
		return this.roleMapper.deleteRolesByIds(roleCodes);
	}

	/**
	 * 配置权限
	 */
	@Override
	public int configAuth(List<Map<String, Object>> list) {
		//先删除然后批量插入
		int i=this.roleMapper.deleteRoleAuthByRoleCode(list.get(0).get("roleCode").toString());
		if(list.get(0).get("clean").toString().equals("0")){
			return this.roleMapper.configAuth(list);
		}
		return i;
	}

	/**
	 * 获取配置角色信息
	 */
	@Override
	public List<Role> getConfigRole(String userCode) {
		return this.roleMapper.getConfigRole(userCode);
	}

}
