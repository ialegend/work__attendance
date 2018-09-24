package com.coder520.user.service;
import com.coder520.common.utils.SecurityUtils;
import com.coder520.user.dao.userMapper;
import com.coder520.user.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JackWangon[www.aiprogram.top] 2017/6/16.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

    @Autowired
    private userMapper userMapper;


    /**
     *@Author JackWang [www.coder520.com]
     *@Date 2017/6/18 12:48
     *@Description 根据用户名查询用户
     */
    @Override
    public user findUserByUserName(String username) {
        user user =userMapper.selectByUserName(username);
        return user;
    }


    @Override
    public void createUser(user user) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        user.setPassword(SecurityUtils.encrptyPassword(user.getPassword()));

        userMapper.insertSelective(user);
    }
}