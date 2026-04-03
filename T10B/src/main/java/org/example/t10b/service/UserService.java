package org.example.t10b.service;

import org.example.t10b.dto.Request.ChangePasswordRequest;
import org.example.t10b.dto.Request.UserRequest;
import org.example.t10b.entity.Role;
import org.example.t10b.entity.User;
import org.example.t10b.exception.BusinessException;
import org.example.t10b.mapper.UserMapper;
import org.example.t10b.repository.RoleRepository;
import org.example.t10b.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }
    @Transactional
    public void addUser(UserRequest userRequest, Set<String> roleNames) {
        if (userRepository.existsByLogin(userRequest.getLogin())) {
            throw new BusinessException("error.user.duplicate");
        }
        Set<Role> roles = roleRepository.findAllByNameIn(roleNames);
        if (roles.isEmpty()) {
            throw new BusinessException("error.role.not_found");
        }
        User entity = userMapper.toEntity(userRequest, roles);
        entity.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.addUser(entity);
    }

    @Transactional
    public void changePassword(String login, ChangePasswordRequest request) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("User not found"));

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new BusinessException("Old password incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void register(UserRequest userRequest) {
        addUser(userRequest, Set.of("ROLE_USER"));
    }
}