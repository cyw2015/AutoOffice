package com.cyw.office.service.person.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.person.EmployeeMapper;
import com.cyw.office.entity.person.Employee;
import com.cyw.office.service.person.IEmpService;
@Service("empService")
public class EmpServiceImpl implements IEmpService {
	@Autowired
	private EmployeeMapper empMapper;
	@Override
	public List<Employee> getEmpPage(Map<String, Object> paramsMap) {
		return this.empMapper.getEmpPage(paramsMap);
	}
	@Override
	public Employee findEmpByCode(String empCode) {
		return this.empMapper.findEmpByCode(empCode);
	}
	@Override
	public int insert(Map<String, Object> paramsMap) {
		return this.empMapper.insert(paramsMap);
	}
	@Override
	public int updateEmploy(Map<String, Object> paramsMap) {
		return this.empMapper.updateEmploy(paramsMap);
	}
	@Override
	public int deleteEmploy(String[] empCodes) {
		return this.empMapper.deleteEmploy(empCodes);
	}

}
