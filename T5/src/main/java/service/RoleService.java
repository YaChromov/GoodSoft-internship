package service;

import dao.RoleRepository;
import factory.RepositoryFactory;
import model.Role;

import java.util.*;
import java.util.stream.Collectors;

public class RoleService {
    private final RoleRepository roleRepository = RepositoryFactory.getInstance().getRoleRepository();

    private static RoleService instance;

    private RoleService(){};

    public static synchronized RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }
        return instance;
    }

    public List<String> getAllRoleNames() {
        return roleRepository.getAllRoles().stream()
                .map(Role::getName)
                .sorted()
                .toList();
    }

    public Set<Role> getRolesByNames(String[] names) {
        if (names == null) return Collections.emptySet();
        return Arrays.stream(names)
                .map(roleRepository::findByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
