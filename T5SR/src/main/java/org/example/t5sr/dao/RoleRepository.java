package org.example.t5sr.dao;


import org.example.t5sr.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleRepository {
    Role findByName(String name);
    Set<Role> getAllRoles();
    Set<Role> findRolesByNames(List<String> names);
}