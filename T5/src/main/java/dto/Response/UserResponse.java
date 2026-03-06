package dto.Response;


import java.time.LocalDate;

public class UserResponse {
        private String login;
        private String email;
        private String surname;
        private String name;
        private String patronymic;
        private LocalDate birthday;
        private model.User.Role role;

        public UserResponse() {
        }


        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
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

        public enum Role {
            ADMIN,
            USER
        }
    }
