package org.example.t5s.service;


import org.example.t5s.dao.RoleRepository;
import org.example.t5s.dao.UserRepository;
import org.example.t5s.dao.impl.UserRepositoryImpl;
import org.example.t5s.dto.Request.UserRequest;

import org.example.t5s.dto.Response.UserResponse;
import org.example.t5s.mapper.UserMapper;
import org.example.t5s.model.Role;
import org.example.t5s.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService {

    private final UserMapper userMapper = new UserMapper();
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired // Необязательно, если конструктор один
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public User authenticate(String login, String password) {
        User user = userRepository.findUserByLogin(login);
        if ((user != null) && Objects.equals(user.getPassword(), password)){
            return user;
        }
        return null;
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        if(Objects.equals(userRepository.findUserByLogin(login).getPassword(),oldPassword)){
            userRepository.updateUserPassword(userRepository.findUserByLogin(login), newPassword);
            return true;
        }
        else return false;
    }

    public UserResponse findUserByLogin(String login){
        return userMapper.toResponse(userRepository.findUserByLogin(login));
    }

    public List<UserResponse> getUserList(){
        List<User>users = userRepository.getAllUsers();
        return userMapper.toResponseList(users);
    }

    public void updateUserData(UserRequest userRequest, String currentUserLogin) {
        if (userRequest != null && userRequest.getLogin() != null) {
            Set<Role> roles = roleRepository.findRolesByNames(userRequest.getRoles());

            if (userRequest.getLogin().equals(currentUserLogin)) {
                Role adminRole = roleRepository.findByName("ADMIN");
                if (adminRole != null) {
                    roles.add(adminRole);
                }
            }

            userRepository.updateUser(userMapper.toEntity(userRequest, roles));
        }
    }

    public void deleteUser(String login){
        User user = userRepository.findUserByLogin(login);
        if(user != null){
            userRepository.deleteUser(user);
        }
    }

    public boolean addUser(UserRequest userRequest) {
        Set<Role> roles = roleRepository.findRolesByNames(userRequest.getRoles());

        if (userRepository.findUserByLogin(userRequest.getLogin()) != null) {
            return false;
        }
        else {
            userRepository.addUser(userMapper.toEntity(userRequest, roles));
            return true;
        }
    }

}
