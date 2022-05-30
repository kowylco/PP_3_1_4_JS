package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Configuration
public class OnStartupConfig {

    private final UserService userService;
    private final RoleService roleService;

    public OnStartupConfig(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addUsersToDB() {
        roleService.saveRole("ROLE_USER");
        roleService.saveRole("ROLE_ADMIN");

        User admin = new User();
        User user = new User();

        Set<Role> rolesAdmin = roleService.getSetOfRoles(new String[]{"ROLE_ADMIN", "ROLE_USER"});
        Set<Role> rolesUser = roleService.getSetOfRoles(new String[]{"ROLE_USER"});

        admin.setId(1);
        admin.setFirstname("Admin");
        admin.setLastname("Adminov");
        admin.setAge((byte) 45);
        admin.setEmail("admin@mail.com");
        admin.setPassword("123");
        admin.setRoles(rolesAdmin);

        user.setId(2);
        user.setFirstname("User");
        user.setLastname("Userov");
        user.setAge((byte) 13);
        user.setEmail("user@mail.com");
        user.setPassword("123");
        user.setRoles(rolesUser);

        userService.saveUser(admin);
        userService.saveUser(user);
    }
}
