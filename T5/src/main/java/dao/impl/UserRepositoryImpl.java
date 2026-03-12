package dao.impl;


import dao.UserRepository;
import model.Role;
import model.User;
import util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepositoryImpl instance;

    private static final String SELECT_ALL_USERS =
            "SELECT u.*, r.id as role_id, r.name as role_name FROM users u " +
                    "LEFT JOIN users_roles ur ON u.login = ur.user_login " +
                    "LEFT JOIN roles r ON ur.role_id = r.id";

    private static final String SELECT_USER_BY_LOGIN = SELECT_ALL_USERS + " WHERE u.login = ?";
    private static final String INSERT_USER = "INSERT INTO users (login, password, email, surname, name, patronymic, birthday) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_USER_ROLE = "INSERT INTO users_roles (user_login, role_id) VALUES (?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET email = ?, surname = ?, name = ?, patronymic = ?, birthday = ? WHERE login = ?";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE login = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE login = ?";
    private static final String DELETE_USER_ROLES = "DELETE FROM users_roles WHERE user_login = ?";

    public static synchronized UserRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    private UserRepositoryImpl() {
    }

    @Override
    public List<User> getAllUsers() {
        Map<String, User> userMap = new LinkedHashMap<>(); // Сохраняем порядок
        try (Connection conn = ConnectionManager.open();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String login = rs.getString("login");
                User user = userMap.computeIfAbsent(login, k -> {
                    try { return mapUser(rs); } catch (SQLException e) { throw new RuntimeException(e); }
                });
                addRoleToUserIfPresent(rs, user);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findUserByLogin(String login) {
        try (Connection conn = ConnectionManager.open();
             PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            User user = null;
            while (rs.next()) {
                if (user == null) user = mapUser(rs);
                addRoleToUserIfPresent(rs, user);
            }
            return user;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void addUser(User user) {
        try (Connection conn = ConnectionManager.open()) {
            conn.setAutoCommit(false); // Транзакция
            try (PreparedStatement psU = conn.prepareStatement(INSERT_USER);
                 PreparedStatement psR = conn.prepareStatement(INSERT_USER_ROLE)) {

                psU.setString(1, user.getLogin());
                psU.setString(2, user.getPassword());
                psU.setString(3, user.getEmail());
                psU.setString(4, user.getSurname());
                psU.setString(5, user.getName());
                psU.setString(6, user.getPatronymic());
                psU.setObject(7, user.getBirthday());
                psU.executeUpdate();

                for (Role role : user.getRoles()) {
                    psR.setString(1, user.getLogin());
                    psR.setLong(2, role.getId());
                    psR.addBatch();
                }
                psR.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void updateUser(User user) {
        try (Connection conn = ConnectionManager.open()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psU = conn.prepareStatement(UPDATE_USER);
                 PreparedStatement psDelR = conn.prepareStatement(DELETE_USER_ROLES);
                 PreparedStatement psInsR = conn.prepareStatement(INSERT_USER_ROLE)) {

                psU.setString(1, user.getEmail());
                psU.setString(2, user.getSurname());
                psU.setString(3, user.getName());
                psU.setString(4, user.getPatronymic());
                psU.setObject(5, user.getBirthday());
                psU.setString(6, user.getLogin());
                int updated = psU.executeUpdate();

                if (updated > 0) {
                    psDelR.setString(1, user.getLogin());
                    psDelR.executeUpdate();

                    for (Role role : user.getRoles()) {
                        psInsR.setString(1, user.getLogin());
                        psInsR.setLong(2, role.getId());
                        psInsR.addBatch();
                    }
                    psInsR.executeBatch();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void updateUserPassword(User user, String newPassword) {
        try (Connection conn = ConnectionManager.open();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PASSWORD)) {
            ps.setString(1, newPassword);
            ps.setString(2, user.getLogin());
            ps.executeUpdate();
            user.setPassword(newPassword); // Сохраняем синхронность объекта
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void deleteUser(User user) {
        try (Connection conn = ConnectionManager.open();
             PreparedStatement ps = conn.prepareStatement(DELETE_USER)) {
            ps.setString(1, user.getLogin());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("login"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("surname"),
                rs.getString("name"),
                rs.getString("patronymic"),
                rs.getObject("birthday", LocalDate.class),
                new HashSet<>()
        );
    }

    private void addRoleToUserIfPresent(ResultSet rs, User user) throws SQLException {
        long roleId = rs.getLong("role_id");
        if (roleId > 0) {
            user.getRoles().add(new Role(roleId, rs.getString("role_name")));
        }
    }
}