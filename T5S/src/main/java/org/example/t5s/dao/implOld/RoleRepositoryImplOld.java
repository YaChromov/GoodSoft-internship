package org.example.t5s.dao.implOld;

import org.example.t5s.dao.RoleRepository;
import org.example.t5s.model.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;


@Repository
@Primary
public class RoleRepositoryImplOld implements RoleRepository {
    private final Map<String, Role> roles = new HashMap<>();

    private RoleRepositoryImplOld() {
        roles.put("ADMIN", new Role(1L, "ADMIN"));
        roles.put("USER", new Role(2L, "USER"));
        roles.put("MODERATOR", new Role(2L, "MODERATOR"));
    }

    @Override
    public Role findByName(String name) {
        return roles.get(name.toUpperCase());
    }

    @Override
    public Set<Role> getAllRoles(){
        return new HashSet<>(roles.values());
    }

    @Override
    public Set<Role> findRolesByNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return Collections.emptySet();
        }

        return names.stream()
                .map(this::findByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}