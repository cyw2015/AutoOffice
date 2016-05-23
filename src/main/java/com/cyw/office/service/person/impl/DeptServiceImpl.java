package com.cyw.office.service.person.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.person.DepartmentMapper;
import com.cyw.office.entity.person.Department;
import com.cyw.office.service.person.IDeptService;
@Service("deptService")
public class DeptServiceImpl implements IDeptService {

	@Autowired
	private DepartmentMapper deptMapper;
	@Override
	public List<Department> getDeptPage(Map<String, Object> paramsMap) {
		return this.deptMapper.getDeptPage(paramsMap);
	}
	@Override
	public Department findDeptByDeptCode(String deptCode) {
		return this.deptMapper.findDeptByDeptCode(deptCode);
	}
	@Override
	public int insert(Map<String, Object> paramsMap) {
		return this.deptMapper.insert(paramsMap);
	}
	@Override
	public int deleteDeptsByCode(String[] deptCodes) {
		return this.deptMapper.deleteDeptsByCode(deptCodes);
	}
	@Override
	public void updateDept(Map<String, Object> paramsMap) {
	  this.deptMapper.updateDept(paramsMap);
	}
	@Override
	public List<Department> getEnterprise(Map<String, Object> paramsMap) {
		return this.deptMapper.getEnterprise(paramsMap);
	}

}
