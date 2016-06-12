package com.cyw.office.service.person;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.person.Employee;

public interface IEmpService {

	/**
	 * 获取员工信息列表
	 * @param paramsMap
	 * @return
	 */
	List<Employee> getEmpPage(Map<String, Object> paramsMap);

	/**
	 * 查找员工
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
	 * 修改员工信息
	 * @param paramsMap
	 */
	int updateEmploy(Map<String, Object> paramsMap);

	/**
	 * 删除员工信息
	 * @param empCodes
	 * @return
	 */
	int deleteEmploy(String[] empCodes);
	/**
	 * 获取联系人
	 * @return
	 */
	List<Employee> getContacts();

}
