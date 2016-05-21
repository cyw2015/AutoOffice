package com.cyw.office.dao.person;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.person.Position;


public interface PositionMapper {

	/**
	 * 获取职位列表
	 * @param paramsMap
	 * @return
	 */
	List<Position> getPosiPage(Map<String, Object> paramsMap);

	/**
	 * 通过名称查询职位
	 * @param posiName
	 * @return
	 */
	Position findPosiByName(String posiName);

	/**
	 * 添加职位信息
	 * @param paramsMap
	 * @return
	 */
	int insert(Map<String, Object> paramsMap);

	/**
	 * 修改职位信息
	 * @param paramsMap
	 * @return
	 */
	int updatePosi(Map<String, Object> paramsMap);

	/**
	 * 删除职位信息
	 * @param ids
	 * @return
	 */
	int deletePosiById(String[] ids);
}