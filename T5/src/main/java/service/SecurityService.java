package service;

import model.User;

import java.util.Objects;

public class SecurityService {
    User hardUser = new User("user", "user");

    public User authenticate(String login, String password) {

        if (Objects.equals(hardUser.getLogin(), login) && Objects.equals(hardUser.getPassword(), password)){
            return hardUser;
        }
        return null;
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if(Objects.equals(user.getPassword(),oldPassword)){
            hardUser.setPassword(newPassword);
            return true;
        }
        else return false;
    }
}
