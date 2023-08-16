package com.example.apidemo.service;

import com.example.apidemo.models.User;
import com.example.apidemo.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    User findByUserName(String userName);
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
    User saveOrUpdateUser(User user);




}
