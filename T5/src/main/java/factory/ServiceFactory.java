package factory;

import dao.UserRepository;
import service.UserService;

public class ServiceFactory {
    private static ServiceFactory instance;

    private UserService userService;

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = UserService.getInstance();
        }
        return userService;
    }

}
