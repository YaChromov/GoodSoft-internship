package factory;


import dao.RoleRepository;
import dao.UserRepository;

public class RepositoryFactory {
        private static RepositoryFactory instance;

        private UserRepository userRepository;
        private RoleRepository roleRepository;

        private RepositoryFactory() {}

        public static RepositoryFactory getInstance() {
            if (instance == null) {
                instance = new RepositoryFactory();
            }
            return instance;
        }

        public UserRepository getUserRepository() {
            if (userRepository == null) {
                userRepository = UserRepository.getInstance();
            }
            return userRepository;
        }

        public RoleRepository getRoleRepository() {
            if (roleRepository == null) {
            roleRepository = RoleRepository.getInstance();
            }
        return roleRepository;
        }
}