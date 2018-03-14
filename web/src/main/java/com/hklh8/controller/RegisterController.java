package com.hklh8.controller;

import com.hklh8.domain.Role;
import com.hklh8.domain.RoleUser;
import com.hklh8.domain.User;
import com.hklh8.properties.AuthorizeConstants;
import com.hklh8.repository.RoleRepository;
import com.hklh8.repository.RoleUserRepository;
import com.hklh8.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;


    @PostMapping("/register")
    public Object register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        System.out.println(user.getUsername() + "," + user.getPassword());
        userRepository.save(user);

        Role role = roleRepository.findByName(AuthorizeConstants.SYS_MANAGER);
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(role);
        roleUser.setBaseUser(user);
        roleUserRepository.save(roleUser);

        return "success";
    }
}
