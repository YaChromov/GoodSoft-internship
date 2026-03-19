package org.example.t5s.dao.implMyBatis;

import org.example.t5s.dao.UserRepository;
import org.example.t5s.dao.daoMapper.UserDao;
import org.example.t5s.model.Role;
import org.example.t5s.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Primary
public class UserRepositoryMyBatisImpl implements UserRepository {

    private final UserDao userDao;

    public UserRepositoryMyBatisImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User findUserByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public void addUser(User user) {
        userDao.insert(user);
        saveUserRoles(user);
    }

    @Override
    public void updateUser(User user) {
        User existing = userDao.findByLogin(user.getLogin());
        if (existing != null) {
            user.setPassword(existing.getPassword());
            userDao.update(user);

            userDao.deleteUserRoles(user.getLogin());
            saveUserRoles(user);
        }
    }

    @Override
    public void updateUserPassword(User user, String newPassword) {
        userDao.updatePassword(user.getLogin(), newPassword);
        user.setPassword(newPassword);
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user.getLogin());
    }


    private void saveUserRoles(User user) {
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (Role role : user.getRoles()) {
                userDao.insertUserRole(user.getLogin(), role.getId());
            }
        }
    }
}