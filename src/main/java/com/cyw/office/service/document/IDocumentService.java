package com.cyw.office.service.document;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.document.Document;

public interface IDocumentService {
	/**
	 * 获取公文详情
	 * @param docCode
	 * @return
	 */
	Map<String, Object> getDocDetail(String docCode);

	/**
	 * 获取可查看的公文
	 * @param paramsMap
	 * @return
	 */
	List<Document> getViewDocPage(Map<String, Object> paramsMap);

	/**
	 * 查看公文json
	 * @param docCode
	 * @return
	 */
	Document findDocDetailByCode(String docCode);

}
