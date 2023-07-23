package com.example.apidemo.service;

import com.example.apidemo.models.User;
import com.example.apidemo.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
