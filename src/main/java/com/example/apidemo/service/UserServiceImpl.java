package com.example.apidemo.service;

import com.example.apidemo.models.Role;
import com.example.apidemo.models.User;
import com.example.apidemo.repositories.UserRepository;
import com.example.apidemo.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements  UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getUserName(), registrationDto.getEmail(),
                registrationDto.getPassword(), registrationDto.getPosition(), registrationDto.getMentor(),
                Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }
}
