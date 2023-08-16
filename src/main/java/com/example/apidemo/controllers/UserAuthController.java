package com.example.apidemo.controllers;

import com.example.apidemo.jwt.JwtTokenProvider;
import com.example.apidemo.models.ERole;
import com.example.apidemo.models.Roles;
import com.example.apidemo.models.User;
import com.example.apidemo.payload.request.LoginRequest;
import com.example.apidemo.payload.request.SignupRequest;
import com.example.apidemo.payload.response.JwtResponse;
import com.example.apidemo.payload.response.MesssageResponse;
import com.example.apidemo.security.CustomUserDetails;
import com.example.apidemo.service.RoleService;
import com.example.apidemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")

    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MesssageResponse("Error: UserName is already"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MesssageResponse("Error: Email is already"));
        }

        User user = new User();
        user.setUserName(signupRequest.getUserName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        Date dateNow = new Date();
        String strNow = sdf.format(dateNow);
        try {
            user.setCreated(sdf.parse(strNow));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Set<String> strRole = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();

        if (strRole==null){
            //Set quyen mac dinh la user
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            strRole.forEach(role -> {
                switch (role){
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "mod":
                        Roles modRole = roleService.findByRoleName(ERole.ROLE_MOD)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(modRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRole(listRoles);
        userService.saveOrUpdateUser(user);
        return ResponseEntity.ok(new MesssageResponse("User registered Successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                            loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            //tao chuoi jwt tra ve client
            String jwt = jwtTokenProvider.generateToken(customUserDetails);
            List<String> listRoles = customUserDetails.getAuthorities().stream()
//                    .map(item -> item.getAuthority()).collect(Collectors.toList());
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt, customUserDetails.getUsername(),
                    customUserDetails.getEmail(), listRoles));
        }catch (AuthenticationException ex) {
            return ResponseEntity.badRequest().body(new MesssageResponse("Invalid username or password"));
        }
    }
}
