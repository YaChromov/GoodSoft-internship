package dto.Request;

import java.time.LocalDate;

public class UserRequest {
    private String login;
    private String password;
    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private model.User.Role role;

    public UserRequest() {
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public model.User.Role getRole() {
        return role;
    }

    public void setRole(model.User.Role role) {
        this.role = role;
    }
}