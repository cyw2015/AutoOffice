package com.cyw.office.dao;

import java.math.BigDecimal;

import com.cyw.office.entity.User;

public interface UserDao {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}