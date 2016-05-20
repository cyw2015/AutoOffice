package com.cyw.office.dao.person;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.person.Department;


public interface DepartmentMapper {

	/**
	 * 获取部门信息列表
	 * @param paramsMap
	 * @return
	 */
	List<Department> getDeptPage(Map<String, Object> paramsMap);

	/**
	 * 通过编号获取部门信息
	 * @param deptCode
	 * @return
	 */
	Department findDeptByDeptCode(String deptCode);

	/**
	 * 添加部门信息
	 * @param paramsMap
	 * @return
	 */
	int insert(Map<String, Object> paramsMap);

	/**
	 * 删除部门信息
	 * @param deptCodes
	 * @return
	 */
	int deleteDeptsByCode(String[] deptCodes);

	/**
	 * 更新部门
	 * @param paramsMap
	 */
	void updateDept(Map<String, Object> paramsMap);
  
}