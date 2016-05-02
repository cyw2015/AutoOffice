package com.cyw.office.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.UserDao;
import com.cyw.office.entity.User;
import com.cyw.office.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserDao userDao;

	public User getUserById(BigDecimal userId) {
		
		return this.userDao.selectByPrimaryKey(userId);
	}

}
