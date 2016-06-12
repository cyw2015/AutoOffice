package com.cyw.office.dao.person;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.person.Employee;


public interface EmployeeMapper {

	/**
	 * 获取员工列表
	 * @param paramsMap
	 * @return
	 */
	List<Employee> getEmpPage(Map<String, Object> paramsMap);

	/**
	 * 通过编号查找员工
	 * @param empCode
	 * @return
	 */
	Employee findEmpByCode(String empCode);

	/**
	 * 插入员工信息
	 * @param paramsMap
	 * @return
	 */
	int insert(Map<String, Object> paramsMap);

	/**
	 * 更新员工信息
	 * @param paramsMap
	 * @return
	 */
	int updateEmploy(Map<String, Object> paramsMap);

	/**
	 * 删除与员工信息
	 * @param empCodes
	 * @return
	 */
	int deleteEmploy(String[] empCodes);

	/**
	 * 获取联系人信息
	 * @return
	 */
	List<Employee> getContacts();
 
}