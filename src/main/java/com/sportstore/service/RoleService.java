package com.sportstore.service;

import com.sportstore.entity.Role;

public interface RoleService {

    public Role createRole(Role role);
    public void deleteRole(Long roleId);
}
