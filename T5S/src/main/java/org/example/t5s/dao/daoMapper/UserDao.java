package org.example.t5s.dao.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.t5s.model.User;

import java.util.List;

@Mapper
public interface UserDao {
    List<User> findAll();
    User findByLogin(@Param("login") String login);
    void insert(User user);
    void update(User user);
    void delete(@Param("login") String login);

    void updatePassword(@Param("login") String login, @Param("newPassword") String newPassword);
    void insertUserRole(@Param("login") String login, @Param("roleId") Long roleId);
    void deleteUserRoles(@Param("login") String login);
}