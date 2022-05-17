package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String start(@AuthenticationPrincipal User principal, @ModelAttribute("new_user") User newUser, @ModelAttribute("edit_user") User editUser, Model model) {
        model.addAttribute("principal", principal);
        model.addAttribute("users", userService.findAll());
        return "/admin";
    }

    @GetMapping("/user/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findByID(id));
        return "/admin/user";
    }

    @GetMapping("/user-create")
    public String createUserForm(@ModelAttribute("user") User user) {
        return "/admin/user-create";
    }

    @PostMapping("/user-create")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user-update/{id}")
    public String userUpdateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findByID(id));
        return "/admin/user-update";
    }

    @PostMapping("/user-update")
    public String userUpdate(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user-delete/{id}")
    public String userDelete(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
