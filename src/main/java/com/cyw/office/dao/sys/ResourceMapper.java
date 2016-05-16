package com.cyw.office.dao.sys;

import java.util.List;

import com.cyw.office.entity.sys.Resource;

public interface ResourceMapper {

	List<Resource> findResByUserCode(String usercode);

	List<Resource> findAllRes();

	List<Resource> findResTree(String id);
   
}