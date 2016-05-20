package com.cyw.office.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.sys.ResourceMapper;
import com.cyw.office.entity.sys.Resource;
import com.cyw.office.service.sys.IResService;
@Service("resService")
public class ResServiceImpl implements IResService {

	@Autowired
	private ResourceMapper resourceMapper;
	
	public List<Resource> findResByUserCode(String usercode) {
		return this.resourceMapper.findResByUserCode(usercode);
	}

	public List<Resource> findAllRes() {
		return this.resourceMapper.findAllRes();
	}

	public List<Resource> findResTree(Map<String, Object> paramsMap) {
		return this.resourceMapper.findResTree(paramsMap);
	}

	public List<Resource> getResPage(Map<String, Object> paramsMap) {
		return this.resourceMapper.getResPage(paramsMap);
	}

	@Override
	public List<Resource> getAllResTree(Map<String, Object> paramsMap) {
		return this.resourceMapper.getAllResTree(paramsMap);
	}

}
