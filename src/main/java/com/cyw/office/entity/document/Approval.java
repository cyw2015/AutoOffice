package com.cyw.office.entity.document;

import java.util.Date;

import com.cyw.office.util.BaseEntity;

/**
 * 审批表
 * 
 * @author cyw
 * 
 */
public class Approval extends BaseEntity {
	private Integer id;
	private String docCode;
	private String docTitle;
	private Date editTime;
	private String creater;
	private String createrName;
	private String createrDept;
	private String approver;// 审批人编号
	private String approverName;// 审批人
	private String approverDept;// 审批人部门
	private Date apprDate;// 审批日期
	private String apprAdvice;// 审批意见
	private String apprState;// 审批状态

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getCreaterDept() {
		return createrDept;
	}

	public void setCreaterDept(String createrDept) {
		this.createrDept = createrDept;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproverDept() {
		return approverDept;
	}

	public void setApproverDept(String approverDept) {
		this.approverDept = approverDept;
	}

	public Date getApprDate() {
		return apprDate;
	}

	public void setApprDate(Date apprDate) {
		this.apprDate = apprDate;
	}

	public String getApprAdvice() {
		return apprAdvice;
	}

	public void setApprAdvice(String apprAdvice) {
		this.apprAdvice = apprAdvice;
	}

	public String getApprState() {
		return apprState;
	}

	public void setApprState(String apprState) {
		this.apprState = apprState;
	}

}