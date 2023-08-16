package com.example.apidemo.service;

import com.example.apidemo.models.ERole;
import com.example.apidemo.models.Intern;
import com.example.apidemo.models.Roles;
import com.example.apidemo.models.User;
import com.example.apidemo.repositories.InternRepository;
import com.example.apidemo.repositories.UserRepository;
import com.example.apidemo.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.apidemo.models.ERole.ROLE_USER;

@Service
public class UserServiceImpl implements  UserService{

    private UserRepository userRepository;

    private InternRepository internRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, InternRepository internRepository) {
        super();
        this.userRepository = userRepository;
        this.internRepository = internRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getUsername(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Collections.singleton(new Roles(ROLE_USER)));
        user = userRepository.save(user);
        Intern intern = new Intern();
//        intern.setUser(user);
        intern.setName("");
        intern.setEmail(user.getEmail());
        intern.setPosition("");
        internRepository.save(intern);

        return user;
    }

    @Override
    public User findByUserName(String userName) {
        return this.userRepository.findByUserName(userName);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return this.userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveOrUpdateUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getListRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
    }
}
