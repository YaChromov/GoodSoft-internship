package org.example.t5sr.model;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.Set;


public class User {
    private String login;
    private String password;
    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.roles = new HashSet<>();
    }

    public User(String login, String password, String email, String surname,
                String name, String patronymic, LocalDate birthday, Set<Role> roles) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.roles = roles != null ? roles : new HashSet<>();
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
}