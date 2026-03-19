package org.example.t5s.service;


import org.example.t5s.dao.RoleRepository;
import org.example.t5s.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<String> getAllRoleNames() {
        return roleRepository.getAllRoles().stream()
                .map(Role::getName)
                .sorted()
                .toList();
    }

    @Transactional(readOnly = true)
    public Set<Role> getRolesByNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return Collections.emptySet();
        }
        return roleRepository.findRolesByNames(names);
    }
}
