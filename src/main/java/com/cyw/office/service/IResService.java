package com.cyw.office.service;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.sys.Resource;


public interface IResService {

	List<Resource> findResByUserCode(String usercode);

	List<Resource> findAllRes();

	List<Resource> findResTree(String id);

	List<Resource> getResPage(Map<String, Object> paramsMap);

}
