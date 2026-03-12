package factory;


import dao.impl.RoleRepositoryImpl;
import dao.impl.UserRepositoryImpl;

public class RepositoryFactory {
        private static RepositoryFactory instance;

        private UserRepositoryImpl userRepositoryImpl;
        private RoleRepositoryImpl roleRepositoryImpl;

        private RepositoryFactory() {}

        public static RepositoryFactory getInstance() {
            if (instance == null) {
                instance = new RepositoryFactory();
            }
            return instance;
        }

        public UserRepositoryImpl getUserRepository() {
            if (userRepositoryImpl == null) {
                userRepositoryImpl = UserRepositoryImpl.getInstance();
            }
            return userRepositoryImpl;
        }

        public RoleRepositoryImpl getRoleRepository() {
            if (roleRepositoryImpl == null) {
            roleRepositoryImpl = RoleRepositoryImpl.getInstance();
            }
        return roleRepositoryImpl;
        }
}