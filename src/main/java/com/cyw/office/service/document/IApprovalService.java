package com.cyw.office.service.document;

import java.util.List;
import java.util.Map;

import com.cyw.office.entity.document.Approval;

public interface IApprovalService {

	/**
	 * 获取审批数据
	 * @param paramsMap
	 * @return
	 */
	List<Approval> getApprovalPage(Map<String, Object> paramsMap);

	/**
	 * 审核
	 * @param paramsMap
	 * @return
	 */
	int updateDocAppr(Map<String, Object> paramsMap);


}
