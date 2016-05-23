package com.cyw.office.dao.document;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.document.Document;

public interface DocumentMapper {

	/**
	 * 获取特定用户创建的公文
	 * @param paramsMap
	 * @return
	 */
	List<Document> getDocPubPage(Map<String, Object> paramsMap);

	/**
	 * 通过编号获取文档
	 * @param docCode
	 * @return
	 */
	Document findDocByCode(String docCode);

	/**
	 * 添加文档信息
	 * @param paramsMap
	 * @return
	 */
	int insert(Map<String, Object> paramsMap);

	/**
	 * 删除公文信息
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
	 * 插入收件人
	 * @param paramsMap
	 * @return
	 */
	int insertRecipients(Map<String, Object> paramsMap);

	/**
	 * 删除收件人
	 * @param paramsMap
	 */
	void deleteRecipientsByCode(Map<String, Object> paramsMap);

	/**
	 * 添加审核表数据
	 * @param paramsMap
	 * @return
	 */
	int addAppr(Map<String, Object> paramsMap);

}