package org.example.t5s.service;


import org.example.t5s.dao.RoleRepository;
import org.example.t5s.dao.UserRepository;
import org.example.t5s.dto.Request.UserRequest;

import org.example.t5s.dto.Response.UserResponse;
import org.example.t5s.exception.BusinessException;
import org.example.t5s.mapper.UserMapper;
import org.example.t5s.model.Role;
import org.example.t5s.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    public User authenticate(String login, String password) {
        User user = userRepository.findUserByLogin(login);
        if (user != null && Objects.equals(user.getPassword(), password)) {
            return user;
        }
        return null;
    }

    @Transactional
    public void changePassword(String login, String oldPassword, String newPassword) {
        User user = userRepository.findUserByLogin(login);

        if (user == null) {
            throw new BusinessException("error.user.notfound");
        }

        if (!Objects.equals(user.getPassword(), oldPassword)) {
            throw new BusinessException("error.password.invalid");
        }

        userRepository.updateUserPassword(user, newPassword);
    }

    public UserResponse findUserByLogin(String login) {
        return userMapper.toResponse(userRepository.findUserByLogin(login));
    }

    public List<UserResponse> getUserList() {
        List<User> users = userRepository.getAllUsers();
        return userMapper.toResponseList(users);
    }

    @Transactional
    public void updateUserData(UserRequest userRequest, String currentUserLogin) {
        if (userRequest == null || userRequest.getLogin() == null) {
            throw new BusinessException("error.user.data.required");
        }

        Set<Role> roles = roleRepository.findRolesByNames(userRequest.getRoles());

        if (userRequest.getLogin().equals(currentUserLogin)) {
            boolean hasAdmin = roles.stream().anyMatch(r -> r.getName().equals("ADMIN"));


            if (!hasAdmin) {
                throw new BusinessException("error.admin.self.demote");
            }
        }

        User existingUser = userRepository.findUserByLogin(userRequest.getLogin());
        if (existingUser == null) {
            throw new BusinessException("error.user.notfound");
        }

        userRepository.updateUser(userMapper.toEntity(userRequest, roles));
    }

    @Transactional
    public void deleteUser(String deleteUserLogin, String currentUserLogin) {
        if (deleteUserLogin.equals(currentUserLogin)) {
            throw new BusinessException("error.delete.self");
        }

        User user = userRepository.findUserByLogin(deleteUserLogin);
        if (user == null) {
            throw new BusinessException("error.user.notfound");
        }

        userRepository.deleteUser(user);
    }

    @Transactional
    public void addUser(UserRequest userRequest) {
        if (userRepository.findUserByLogin(userRequest.getLogin()) != null) {
            throw new BusinessException("error.user.duplicate");
        }
        Set<Role> roles = roleRepository.findRolesByNames(userRequest.getRoles());
        userRepository.addUser(userMapper.toEntity(userRequest, roles));
    }
}

