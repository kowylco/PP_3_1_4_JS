package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private final UserService userService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String start(@AuthenticationPrincipal User principal, @ModelAttribute("new_user") User newUser, Model model) {
        model.addAttribute("principal", principal);
        model.addAttribute("users", userService.findAll().stream().sorted(Comparator.comparingLong(User::getId)).collect(Collectors.toList()));
        model.addAttribute("user", principal);
        return "/admin";
    }

    @PostMapping("/user-create")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/user-update")
    public String userUpdate(@ModelAttribute("edit_user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user-delete/{id}")
    public String userDelete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

}
