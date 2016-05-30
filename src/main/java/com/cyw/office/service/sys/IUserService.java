package com.cyw.office.service.sys;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.sys.User;

public interface IUserService {
	public User findByUserCode(String usercode);

	public List<User> getUserPage(Map<String, Object> paramsMap);

	public void insert(Map<String, Object> paramsMap);

	public int updateByPrimaryKey(Map<String, Object> paramsMap);

	public int resetPassword(Map<String, Object> paramsMap);

	public int deleteUsers(String[] userCodes);

	public int configRoleSave(List<Map<String, Object>> list);

}
