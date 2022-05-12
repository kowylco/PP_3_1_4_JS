package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;
    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String start() {
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        model.addAttribute("users", service.findAll());
        return "/admin/users";
    }

    @GetMapping("/user/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", service.findByID(id));
        return "/admin/user";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "/admin/user-create";
    }

    @PostMapping("/user-create")
    public String saveUser(User user) {
        service.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user-update/{id}")
    public String userUpdateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", service.findByID(id));
        return "/admin/user-update";
    }

    @PostMapping("/user-update")
    public String userUpdate(User user) {
        service.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user-delete/{id}")
    public String userDelete(@PathVariable("id") int id) {
        service.deleteById(id);
        return "redirect:/admin/users";
    }
}
