package com.cyw.office.service.sys;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.sys.Authority;

public interface IAuthService {

	List<Authority> getAuthPage(Map<String, Object> paramsMap);

	Authority findByAuthCode(String authCode);

	int insert(Map<String, Object> paramsMap);

	int updateByAuthCode(Map<String, Object> paramsMap);

	int deleteAuthsByIds(String[] authCodes);

	int configRes(List<Map<String, Object>> list);

	List<Authority> getAllAuthWithRole(String roleCode);

}
