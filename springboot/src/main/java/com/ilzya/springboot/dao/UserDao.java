package com.ilzya.springboot.dao;

import com.ilzya.springboot.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    /**
     * 登录
     * @param username
     * @return
     */
    @Select(value = "SELECT * FROM user WHERE username = #{username}")
    User login(String username);


}
