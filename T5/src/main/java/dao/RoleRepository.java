package dao;

import model.Role;

import java.util.*;
import java.util.stream.Collectors;

public class RoleRepository {
    private final Map<String, Role> roles = new HashMap<>();
    private static RoleRepository instance;

    public static synchronized RoleRepository getInstance() {
        if (instance == null) {
            instance = new RoleRepository();
        }
        return instance;
    }

    private RoleRepository() {
        roles.put("ADMIN", new Role(1L, "ADMIN"));
        roles.put("USER", new Role(2L, "USER"));
        roles.put("MODERATOR", new Role(2L, "MODERATOR"));
    }

    public Role findByName(String name) {
        return roles.get(name.toUpperCase());
    }

    public void addRole(String name) {
        long newId = roles.size() + 1;
        roles.put(name.toUpperCase(), new Role(newId, name.toUpperCase()));
    }

    public Set<Role> getAllRoles(){
        return new HashSet<>(roles.values());
    }

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