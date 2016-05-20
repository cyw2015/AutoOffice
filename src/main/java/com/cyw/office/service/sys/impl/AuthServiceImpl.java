package com.cyw.office.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.sys.AuthorityMapper;
import com.cyw.office.entity.sys.Authority;
import com.cyw.office.service.sys.IAuthService;

@Service("authService")
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private AuthorityMapper authMapper;
	/**
	 * 获取权限数据
	 */
	@Override
	public List<Authority> getAuthPage(Map<String, Object> paramsMap) {
		return this.authMapper.getAuthPage(paramsMap);
	}
	
	/**
	 * 查询权限是否重复
	 */
	@Override
	public Authority findByAuthCode(String authCode) {
		return this.authMapper.findByAuthCode(authCode);
	}
	
	/**
	 * 插入权限信息
	 */
	@Override
	public int insert(Map<String, Object> paramsMap) {
		return this.authMapper.insert(paramsMap);
	}

	/**
	 * 更新权限信息
	 */
	@Override
	public int updateByAuthCode(Map<String, Object> paramsMap) {
		return this.authMapper.updateByAuthCode(paramsMap);
	}

	/**
	 * 删除权限信息
	 */
	@Override
	public int deleteAuthsByIds(String[] authCodes) {
		return this.authMapper.deleteAuthsByIds(authCodes);
	}

	/**
	 * 配置资源
	 */
	@Override
	public int configRes(List<Map<String, Object>> list) {
		//先删除然后批量插入
		int i = this.authMapper.deleteAuthResByAuthCode(list.get(0).get("authCode").toString());
		if(list.get(0).get("clean").toString().equals("0")){
			//不为空则插入
			return this.authMapper.configRes(list);
		}
		return i;
	}

	/**
	 * 获取所有权限列表
	 */
	@Override
	public List<Authority> getAllAuthWithRole(String roleCode) {
		return this.authMapper.getAllAuthWithRole(roleCode);
	}

}
