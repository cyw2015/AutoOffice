package com.cyw.office.service.document.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.document.DocumentMapper;
import com.cyw.office.entity.document.Document;
import com.cyw.office.service.document.IPublishService;
@Service("pubService")
public class PublishServiceImpl implements IPublishService {

	@Autowired
	private DocumentMapper documentMapper;
	@Override
	public List<Document> getDocPubPage(Map<String, Object> paramsMap) {
		return this.documentMapper.getDocPubPage(paramsMap);
	}
	@Override
	public Document findDocByCode(String docCode) {
		return this.documentMapper.findDocByCode(docCode);
	}

	@Override
	public int insert(Map<String, Object> paramsMap) {
		int i =this.documentMapper.insert(paramsMap);
		this.documentMapper.insertRecipients(paramsMap);
		return i;
	}
	@Override
	public int deleteDocByCode(Map<String, Object> paramsMap) {
		return this.documentMapper.deleteDocByCode(paramsMap);
	}
	@Override
	public int updateDocPub(Map<String, Object> paramsMap) {
		//更新
		int i = this.documentMapper.updateDocPub(paramsMap);
		this.documentMapper.deleteRecipientsByCode(paramsMap);
		this.documentMapper.insertRecipients(paramsMap);
		return i;
	}

}
