package com.example.apidemo.service;

import com.example.apidemo.models.User;
import com.example.apidemo.web.dto.UserRegistrationDto;

public interface UserService {
    User save(UserRegistrationDto registrationDto);
}
