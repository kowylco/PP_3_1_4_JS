package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByID(long id) {
        return repository.getById(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }
}
