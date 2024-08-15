package com.mobiliz.version1.Service;

import com.mobiliz.version1.Model.User;
import com.mobiliz.version1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User findUsersByName(String name) {
        return userRepository.findUsersByName(name);
    }

    public User registerUser(Integer id, String name, String email, String password) {
        String encryptedPassword = passwordEncoder.encode(password);
        User user = new User(id, name, email, encryptedPassword);
        return userRepository.save(user);
    }

    public boolean authenticateUser(String name, String password) {
        User user = userRepository.findUsersByName(name);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        System.out.println("Authentication failed for user: " + name);
        return false;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}

