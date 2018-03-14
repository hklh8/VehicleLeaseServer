package com.hklh8.controller;

import com.hklh8.domain.User;
import com.hklh8.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Object login() {




        return "success";
    }

    @PostMapping("/register")
    public Object register() {

//        userRepository.save(new User());

        return "success";
    }

}
