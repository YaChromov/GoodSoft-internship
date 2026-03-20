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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void changePassword(String login, String oldPassword, String newPassword) {
        User user = userRepository.findUserByLogin(login);

        if (user == null) {
            throw new BusinessException("error.user.notfound");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("error.password.invalid");
        }

        userRepository.updateUserPassword(user, passwordEncoder.encode(newPassword));
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

        User updatedEntity = userMapper.toEntity(userRequest, roles);
        userRepository.updateUser(updatedEntity);
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
        User entity = userMapper.toEntity(userRequest, roles);
        entity.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.addUser(entity);
    }
}

