package com.cyw.office.entity.person;

import com.cyw.office.util.BaseEntity;

public class Position extends BaseEntity{
    private Integer id;

    private String posiName;

    private String posiDesc;

    private Short posiLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosiName() {
        return posiName;
    }

    public void setPosiName(String posiName) {
        this.posiName = posiName == null ? null : posiName.trim();
    }

    public String getPosiDesc() {
        return posiDesc;
    }

    public void setPosiDesc(String posiDesc) {
        this.posiDesc = posiDesc == null ? null : posiDesc.trim();
    }

    public Short getPosiLevel() {
        return posiLevel;
    }

    public void setPosiLevel(Short posiLevel) {
        this.posiLevel = posiLevel;
    }
}