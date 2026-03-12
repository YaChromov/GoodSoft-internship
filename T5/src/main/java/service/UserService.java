package service;

import dao.impl.RoleRepositoryImpl;
import dao.impl.UserRepositoryImpl;
import dto.Request.UserRequest;
import dto.Response.UserResponse;
import factory.RepositoryFactory;
import mapper.UserMapper;
import model.Role;
import model.User;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UserService {

    private final UserRepositoryImpl userRepositoryImpl = RepositoryFactory.getInstance().getUserRepository();
    private final RoleRepositoryImpl roleRepositoryImpl = RepositoryFactory.getInstance().getRoleRepository();
    private final UserMapper userMapper = new UserMapper();

    private static UserService instance;

    private UserService(){
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }


    public User authenticate(String login, String password) {
        User user = userRepositoryImpl.findUserByLogin(login);
        if ((user != null) && Objects.equals(user.getPassword(), password)){
            return user;
        }
        return null;
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        if(Objects.equals(userRepositoryImpl.findUserByLogin(login).getPassword(),oldPassword)){
            userRepositoryImpl.updateUserPassword(userRepositoryImpl.findUserByLogin(login), newPassword);
            return true;
        }
        else return false;
    }

    public UserResponse findUserByLogin(String login){
        return userMapper.toResponse(userRepositoryImpl.findUserByLogin(login));
    }

    public List<UserResponse> getUserList(){
        List<User>users = userRepositoryImpl.getAllUsers();
        return userMapper.toResponseList(users);
    }

    public void updateUserData(UserRequest userRequest, String currentUserLogin) {
        if (userRequest != null && userRequest.getLogin() != null) {
            Set<Role> roles = roleRepositoryImpl.findRolesByNames(userRequest.getRoles());

            if (userRequest.getLogin().equals(currentUserLogin)) {
                Role adminRole = roleRepositoryImpl.findByName("ADMIN");
                if (adminRole != null) {
                    roles.add(adminRole);
                }
            }

            userRepositoryImpl.updateUser(userMapper.toEntity(userRequest, roles));
        }
    }

    public void deleteUser(String login){
        if(userRepositoryImpl.findUserByLogin(login) != null){
            userRepositoryImpl.deleteUser(userRepositoryImpl.findUserByLogin(login));
        }
    }

    public boolean addUser(UserRequest userRequest) {
        Set<Role> roles = roleRepositoryImpl.findRolesByNames(userRequest.getRoles());

        if (userRepositoryImpl.findUserByLogin(userRequest.getLogin()) != null) {
            return false;
        }
        else {
            userRepositoryImpl.addUser(userMapper.toEntity(userRequest, roles));
            return true;
        }
    }


}
