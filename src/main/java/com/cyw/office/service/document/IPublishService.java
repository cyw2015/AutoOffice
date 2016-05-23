package com.cyw.office.service.document;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.document.Document;

public interface IPublishService {

	/**
	 * 获取自己编辑或发布的公文
	 * @param paramsMap
	 * @return
	 */
	List<Document> getDocPubPage(Map<String, Object> paramsMap);

	/**
	 * 根据编号查询公文
	 * @param docCode
	 * @return
	 */
	Document findDocByCode(String docCode);

	/**
	 * 添加公文
	 * @param paramsMap
	 */
	int insert(Map<String, Object> paramsMap);

	/**
	 * 删除公文
	 * @param paramsMap
	 * @return
	 */
	int deleteDocByCode(Map<String, Object> paramsMap);

	/**
	 * 更新公文
	 * @param paramsMap
	 * @return
	 */
	int updateDocPub(Map<String, Object> paramsMap);

	/**
	 * 送审
	 * @param docCode
	 * @return
	 */
	int sendAppr(Map<String, Object> paramsMap);

}
