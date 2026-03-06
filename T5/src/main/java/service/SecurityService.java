package service;

import dao.UserRepository;
import dto.Request.UserRequest;
import dto.Response.UserResponse;
import mapper.UserMapper;
import model.User;

import java.util.List;
import java.util.Objects;

public class SecurityService {

    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapper = new UserMapper();


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

    public User findUserByLogin(String login){
        return userRepository.findUserByLogin(login);
    }

    public List<UserResponse> getUserList(){
        List<User>users = userRepository.getAllUsers();
        return userMapper.toResponseList(users);
    }

    public void updateUser(UserRequest userRequest) {
        if (userRequest != null && userRequest.getLogin() != null) {
            userRepository.updateUser(userMapper.toEntity(userRequest));
        }
    }

    public boolean deleteUser(String login){
        if(userRepository.findUserByLogin(login) != null){
            userRepository.deleteUser(findUserByLogin(login));
            return true;
        }
        else return false;
    }

    public boolean addUser(UserRequest userRequest) {

        if (userRepository.findUserByLogin(userRequest.getLogin()) != null) {
            return false;
        }
        else {userRepository.addUser(userMapper.toEntity(userRequest));
            return true;
        }
    }


}
