package factory;

import service.RoleService;
import service.UserService;

public class ServiceFactory {
    private static ServiceFactory instance;

    private UserService userService;
    private RoleService roleService;

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

    public RoleService getRoleService(){
        if (roleService == null){
            roleService = RoleService.getInstance();
        }
        return roleService;
    }

}
