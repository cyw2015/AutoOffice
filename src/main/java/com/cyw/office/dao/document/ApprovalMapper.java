package com.cyw.office.dao.document;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.document.Approval;


public interface ApprovalMapper {

	/**
	 * 获取审批页面
	 * @param paramsMap
	 * @return
	 */
	List<Approval> getApprovalPage(Map<String, Object> paramsMap);

	/**
	 * 审批
	 * @param paramsMap
	 * @return
	 */
	int updateDocAppr(Map<String, Object> paramsMap);

	/**
	 * 更新公文状态
	 * @param paramsMap
	 * @return
	 */
	int  updateDocState(Map<String, Object> paramsMap);
   
}