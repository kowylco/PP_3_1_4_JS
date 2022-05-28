package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserService userService;
    private final RoleService roleService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserService userService, RoleService roleService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user", "/api/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/**").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addUsersToDB() {
        roleService.saveRole("ROLE_USER");
        roleService.saveRole("ROLE_ADMIN");

        User admin = new User();
        User user = new User();

        Set<Role> rolesAdmin = roleService.getSetOfRoles(new String[]{"ROLE_ADMIN", "ROLE_USER"});
        Set<Role> rolesUser = roleService.getSetOfRoles(new String[]{"ROLE_USER"});

        admin.setFirstname("Admin");
        admin.setLastname("Adminov");
        admin.setAge((byte) 45);
        admin.setEmail("admin@mail.com");
        admin.setPassword("$2a$12$BfPCcRWyWhO4ysHKQbe79.hQEeG6nkmUJ/9aVcmkxV4FRgXhMpx9W");
        admin.setRoles(rolesAdmin);

        user.setFirstname("User");
        user.setLastname("Userov");
        user.setAge((byte) 13);
        user.setEmail("user@mail.com");
        user.setPassword("$2a$12$BfPCcRWyWhO4ysHKQbe79.hQEeG6nkmUJ/9aVcmkxV4FRgXhMpx9W");
        user.setRoles(rolesUser);

        userService.saveUser(admin);
        userService.saveUser(user);
    }
}