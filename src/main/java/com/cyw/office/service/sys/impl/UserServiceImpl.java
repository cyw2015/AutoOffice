package com.cyw.office.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.sys.UserMapper;
import com.cyw.office.entity.sys.User;
import com.cyw.office.service.sys.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	public User findByUserCode(String usercode) {
		return this.userMapper.findByUserCode(usercode);
	}

	/**
	 * 用户数据
	 */
	public List<User> getUserPage(Map<String, Object> paramsMap) {
		return this.userMapper.getUserPage(paramsMap);
	}

	public void insert(Map<String, Object> paramsMap) {
		this.userMapper.insert(paramsMap);
	}

	/**
	 * 更新用户
	 */
	public int updateByPrimaryKey(Map<String, Object> paramsMap) {
		return this.userMapper.updateByPrimaryKey(paramsMap);
	}

	public int resetPassword(Map<String, Object> paramsMap) {
		return this.userMapper.resetPassword(paramsMap);
	}

	public int deleteUsers(String[] userCodes) {
		return this.userMapper.deleteUsers(userCodes);
	}

}
