package com.cyw.office.dao.sys;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.sys.User;


public interface UserMapper {
	 User findByUserCode(String usercode);

	List<User> getUserPage(Map<String, Object> paramsMap);

	void insert(Map<String, Object> paramsMap);
}