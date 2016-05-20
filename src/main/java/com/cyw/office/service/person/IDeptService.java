package com.cyw.office.service.person;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.person.Department;

public interface IDeptService {

	/**
	 * 获取部门信息列表
	 * @param paramsMap
	 * @return
	 */
	List<Department> getDeptPage(Map<String, Object> paramsMap);

	/**
	 * 通过编号查找部门信息
	 * @param deptCode
	 * @return
	 */
	Department findDeptByDeptCode(String deptCode);

	/**
	 *添加部门信息
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
	 * 修改部门信息
	 * @param paramsMap
	 * @return
	 */
	void  updateDept(Map<String, Object> paramsMap);

}
