package com.cyw.office.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.sys.UserMapper;
import com.cyw.office.entity.sys.User;
import com.cyw.office.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	public User findByUserCode(String usercode) {
		return this.userMapper.findByUserCode(usercode);
	}

}
