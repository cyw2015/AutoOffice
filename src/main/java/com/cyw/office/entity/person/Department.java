package com.cyw.office.entity.person;

import com.cyw.office.util.BaseEntity;

public class Department extends BaseEntity{
    private String deptCode;

    private Integer id;

    private String deptName;

    private String deptDesc;

    private String superDept;
    private String state;

    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc == null ? null : deptDesc.trim();
    }

    public String getSuperDept() {
        return superDept;
    }

    public void setSuperDept(String superDept) {
        this.superDept = superDept == null ? null : superDept.trim();
    }
}