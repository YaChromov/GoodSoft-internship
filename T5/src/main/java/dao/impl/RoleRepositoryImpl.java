package dao.impl;

import dao.RoleRepository;
import model.Role;
import util.ConnectionManager;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RoleRepositoryImpl implements RoleRepository {
    private static RoleRepositoryImpl instance;

    private static final String SELECT_BY_NAME = "SELECT id, name FROM roles WHERE UPPER(name) = ?";
    private static final String INSERT_ROLE = "INSERT INTO roles (name) VALUES (?) ON CONFLICT (name) DO NOTHING";
    private static final String SELECT_ALL = "SELECT id, name FROM roles";

    public static synchronized RoleRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new RoleRepositoryImpl();
        }
        return instance;
    }

    private RoleRepositoryImpl() {
    }

    @Override
    public Role findByName(String name) {
        if (name == null) return null;

        try (Connection conn = ConnectionManager.open();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_NAME)) {

            ps.setString(1, name.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Role(
                            rs.getLong("id"),
                            rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске роли по имени: " + name, e);
        }
        return null;
    }

    @Override
    public Set<Role> getAllRoles() {
        Set<Role> roles = new HashSet<>();

        try (Connection conn = ConnectionManager.open();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL)) {

            while (rs.next()) {
                roles.add(new Role(
                        rs.getLong("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех ролей", e);
        }
        return roles;
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