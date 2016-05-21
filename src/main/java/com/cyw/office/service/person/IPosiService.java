package com.cyw.office.service.person;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.person.Position;

public interface IPosiService {

	/**
	 * 获取岗位信息
	 * @param paramsMap
	 * @return
	 */
	List<Position> getPosiPage(Map<String, Object> paramsMap);

	/**
	 * 通过名称查找职位
	 * @param posiName
	 * @return
	 */
	Position findPosiByName(String posiName);

	/**
	 * 增加岗位信息
	 * @param paramsMap
	 * @return
	 */
	int insert(Map<String, Object> paramsMap);

	/**
	 * 修改岗位信息
	 * @param paramsMap
	 * @return
	 */
	int updatePosi(Map<String, Object> paramsMap);
	
	/**
	 * 删除岗位信息
	 * @param posiCodes
	 * @return
	 */
	int deletePosiById(String[] ids);

}
