package com.cyw.office.service;

import com.cyw.office.entity.sys.User;

public interface IUserService {
	public User findByUserCode(String usercode);
}
