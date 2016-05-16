package com.cyw.office.service;

import java.util.List;

import com.cyw.office.entity.sys.Resource;


public interface IResService {

	List<Resource> findResByUserCode(String usercode);

	List<Resource> findAllRes();

}
