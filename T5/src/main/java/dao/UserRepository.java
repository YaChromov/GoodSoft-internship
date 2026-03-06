package dao;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.User;
// pov создал временную затычку, чтобы заложить фундамент работы с СУБД по всем канонам MVC
//ps прекрасно знаю про принципы DRY KISS YAGNI и тд
// но тк уже проспойлерил себе будущую работу с бд, решил не усложнять себе жизнь

public class UserRepository {
private final HashMap<String, User> userMap = new HashMap<>();

    private static UserRepository instance;

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

private UserRepository(){
    User hardUser = new User("user", "user", "bobe@mail.ru", "Иванов", "Иван","Иваныч", LocalDate.of(2024, 1, 15), User.Role.USER);
    User hardAdmin = new User("admin", "admin", "email@mail.ru", "ANO", "NY", "M", LocalDate.of(2024, 1, 15), User.Role.ADMIN);
    userMap.put(hardUser.getLogin(), hardUser);
    userMap.put(hardAdmin.getLogin(), hardAdmin);
}

public List<User> getAllUsers(){
    return new ArrayList<>(userMap.values());
}

public User findUserByLogin(String login){
    return userMap.get(login);
}

public void updateUser(User user) {
        if (userMap.containsKey(user.getLogin())) {
            user.setPassword(userMap.get(user.getLogin()).getPassword());
            userMap.put(user.getLogin(), user);
        }
}

public void addUser(User user){
    userMap.put(user.getLogin(), user);
}

public void updateUserPassword(User user, String newPassword){
    user.setPassword(newPassword);
    userMap.put(user.getLogin(), user);
}

public void deleteUser(User user){
    userMap.remove(user.getLogin());
}
}



