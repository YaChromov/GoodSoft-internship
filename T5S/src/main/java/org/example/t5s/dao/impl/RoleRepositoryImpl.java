package org.example.t5s.dao.impl;

import org.example.t5s.dao.RoleRepository;
import org.example.t5s.model.Role;
import org.example.t5s.util.ConnectionManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.*;


@Repository
public class RoleRepositoryImpl implements RoleRepository {


    private static final String SELECT_BY_NAME = "SELECT id, name FROM roles WHERE UPPER(name) = ?";
    private static final String INSERT_ROLE = "INSERT INTO roles (name) VALUES (?) ON CONFLICT (name) DO NOTHING";
    private static final String SELECT_ALL = "SELECT id, name FROM roles";
    private static final String SELECT_ANY = "SELECT id, name FROM roles WHERE UPPER(name) = ANY(?)";



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
        Set<Role> roles = new HashSet<>();

        try (Connection conn = ConnectionManager.open();
             PreparedStatement ps = conn.prepareStatement(SELECT_ANY)) {
            String[] namesArray = names.stream()
                    .filter(Objects::nonNull)
                    .map(String::toUpperCase)
                    .toArray(String[]::new);

            java.sql.Array sqlArray = conn.createArrayOf("VARCHAR", namesArray);
            ps.setArray(1, sqlArray);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roles.add(new Role(
                            rs.getLong("id"),
                            rs.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при пакетном поиске ролей", e);
        }
        return roles;
    }
}