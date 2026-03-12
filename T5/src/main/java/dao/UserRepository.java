package dao;

import model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User findUserByLogin(String login);
    void addUser(User user);
    void updateUser(User user);
    void updateUserPassword(User user, String newPassword);
    void deleteUser(User user);
}