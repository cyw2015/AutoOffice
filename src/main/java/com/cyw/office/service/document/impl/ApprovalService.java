package com.cyw.office.service.document.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.office.dao.document.ApprovalMapper;
import com.cyw.office.entity.document.Approval;
import com.cyw.office.service.document.IApprovalService;
@Service("apprService")
public class ApprovalService implements IApprovalService {

	
	@Autowired
	private ApprovalMapper approvalMapper;
	@Override
	public List<Approval> getApprovalPage(Map<String, Object> paramsMap) {
		return this.approvalMapper.getApprovalPage(paramsMap);
	}
	@Override
	public int updateDocAppr(Map<String, Object> paramsMap) {
		int  i=this.approvalMapper.updateDocAppr(paramsMap);
		if(i!=0){
			this.approvalMapper.updateDocState(paramsMap);
		}
		return i;
	}

}
