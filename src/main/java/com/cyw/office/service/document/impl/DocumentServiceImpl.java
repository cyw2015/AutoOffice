package com.cyw.office.service.document.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.document.DocumentMapper;
import com.cyw.office.entity.document.Document;
import com.cyw.office.service.document.IDocumentService;
@Service("docService")
public class DocumentServiceImpl implements IDocumentService{
	@Autowired
	private DocumentMapper documentMapper;
	@Override
	public Map<String, Object> getDocDetail(String docCode) {
		return this.documentMapper.getDocDetail(docCode);
	}
	@Override
	public List<Document> getViewDocPage(Map<String, Object> paramsMap) {
		return this.documentMapper.getViewDocPage(paramsMap);
	}

}
