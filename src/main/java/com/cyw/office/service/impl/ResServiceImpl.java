package com.cyw.office.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.sys.ResourceMapper;
import com.cyw.office.entity.sys.Resource;
import com.cyw.office.service.IResService;
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

}