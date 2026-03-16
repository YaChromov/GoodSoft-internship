package org.example.t5s.dao.implOld;


import org.example.t5s.dao.UserRepository;
import org.example.t5s.model.Role;
import org.example.t5s.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;


// pov создал временную затычку, чтобы заложить фундамент работы с СУБД по всем канонам MVC
//ps прекрасно знаю про принципы DRY KISS YAGNI и тд
// но тк уже проспойлерил себе будущую работу с бд, решил не усложнять себе жизнь


@Repository
@Primary
public class UserRepositoryImplOld implements UserRepository {
private final    HashMap<String, User> userMap = new HashMap<>();


public UserRepositoryImplOld(){
    Role roleUser = new Role(1L, "USER");
    Role roleAdmin = new Role(2L, "ADMIN");
    User hardUser = new User("user", "user", "bobe@mail.ru", "Иванов", "Иван","Иваныч", LocalDate.of(2024, 1, 15), new HashSet<>(Set.of(roleUser)));
    User hardAdmin = new User("admin", "admin", "email@mail.ru", "ANO", "NY", "M", LocalDate.of(2024, 1, 15), new HashSet<>(Set.of(roleUser, roleAdmin)));
    userMap.put(hardUser.getLogin(), hardUser);
    userMap.put(hardAdmin.getLogin(), hardAdmin);
}
@Override
public List<User> getAllUsers(){
    return new ArrayList<>(userMap.values());
}

@Override
public User findUserByLogin(String login){
    return userMap.get(login);
}

@Override
public void updateUser(User user) {
        if (userMap.containsKey(user.getLogin())) {
            user.setPassword(userMap.get(user.getLogin()).getPassword());
            userMap.put(user.getLogin(), user);
        }
}

@Override
public void addUser(User user){
    userMap.put(user.getLogin(), user);
}

@Override
public void updateUserPassword(User user, String newPassword){
    user.setPassword(newPassword);
    userMap.put(user.getLogin(), user);
}

@Override
public void deleteUser(User user){
    userMap.remove(user.getLogin());
}
}



