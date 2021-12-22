package com.belous.crmdemo.dao;

import com.belous.crmdemo.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User theUser);
}
