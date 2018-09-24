package com.coder520.user.dao;

import com.coder520.user.entity.user;

public interface userMapper {
    int deleteByPrimaryKey(Long id);

    int insert(user record);

    int insertSelective(user record);

    user selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(user record);

    int updateByPrimaryKey(user record);

    /**
     * 根据用户名查询用户
     * @return
     */
    user selectByUserName(String username);
}