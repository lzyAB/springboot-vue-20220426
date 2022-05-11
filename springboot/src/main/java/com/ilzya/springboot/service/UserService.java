package com.ilzya.springboot.service;

import com.ilzya.springboot.dao.UserDao;
import com.ilzya.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     * 登录 查询是否有该用户 返回该用户信息
     * @param username
     * @return
     */
    public User login(String username){
        return userDao.login(username);
    }


}
