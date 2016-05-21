package com.cyw.office.service.person.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.person.PositionMapper;
import com.cyw.office.entity.person.Position;
import com.cyw.office.service.person.IPosiService;
@Service("posiService")
public class PosiServiceImpl implements IPosiService {

	@Autowired
	private PositionMapper posiMapper;
	@Override
	public List<Position> getPosiPage(Map<String, Object> paramsMap) {
		return this.posiMapper.getPosiPage(paramsMap);
	}
	@Override
	public Position findPosiByName(String posiName) {
		return this.posiMapper.findPosiByName(posiName);
	}
	@Override
	public int insert(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return this.posiMapper.insert(paramsMap);
	}
	@Override
	public int updatePosi(Map<String, Object> paramsMap) {
		return this.posiMapper.updatePosi(paramsMap);
	}
	@Override
	public int deletePosiById(String[] ids) {
		return this.posiMapper.deletePosiById(ids);
	}

}
