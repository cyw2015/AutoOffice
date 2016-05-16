package com.cyw.office.dao.sys;

import com.cyw.office.entity.sys.User;


public interface UserMapper {
	 User findByUserCode(String usercode);
}