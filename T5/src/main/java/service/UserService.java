package service;

import dao.UserRepository;
import dto.Request.UserRequest;
import dto.Response.UserResponse;
import factory.RepositoryFactory;
import mapper.UserMapper;
import model.User;

import java.util.List;
import java.util.Objects;

public class UserService {

    private final UserRepository userRepository = RepositoryFactory.getInstance().getUserRepository();
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

    public void updateUserData(UserRequest userRequest) {
        if (userRequest != null && userRequest.getLogin() != null) {
            userRepository.updateUser(userMapper.toEntity(userRequest));
        }
    }

    public void deleteUser(String login){
        if(userRepository.findUserByLogin(login) != null){
            userRepository.deleteUser(findUserByLogin(login));
        }
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
