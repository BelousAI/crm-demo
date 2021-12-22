package com.belous.crmdemo.dao;

import com.belous.crmdemo.entity.Role;

public interface RoleDao {

    Role findRoleByName(String roleName);
}
