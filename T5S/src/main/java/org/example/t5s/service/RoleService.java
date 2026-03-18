package org.example.t5s.service;


import org.example.t5s.dao.RoleRepository;
import org.example.t5s.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }


    public List<String> getAllRoleNames() {
        return roleRepository.getAllRoles().stream()
                .map(Role::getName)
                .sorted()
                .toList();
    }

    public Set<Role> getRolesByNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return Collections.emptySet();
        }
        return roleRepository.findRolesByNames(names);
    }
}
