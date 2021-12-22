package com.belous.crmdemo.service;

import com.belous.crmdemo.entity.User;
import com.belous.crmdemo.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void save(CrmUser theCrmUser);
}
