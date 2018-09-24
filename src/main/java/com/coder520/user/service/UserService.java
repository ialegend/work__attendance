package com.coder520.user.service;

import com.coder520.user.entity.user;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JackWangon[www.aiprogram.top] 2017/6/16.
 */
public interface UserService {


    user findUserByUserName(String username);

    void createUser(user user) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
