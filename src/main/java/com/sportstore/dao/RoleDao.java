package com.sportstore.dao;

import com.sportstore.entity.Role;

public interface RoleDao {

    public Role createRole(Role role);
    public void deleteRole(Long roleId);
}
