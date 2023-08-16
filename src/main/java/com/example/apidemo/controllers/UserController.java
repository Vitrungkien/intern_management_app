package com.example.apidemo.controllers;

import com.example.apidemo.models.ResponseObject;
import com.example.apidemo.models.User;
import com.example.apidemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //lay danh sach toan bo user
    @GetMapping("")
    //this request is: http://localhost:8080/api/v1/Users
    List<User> getAllUser() {
        return userRepository.findAll();
    }

    //tim kiem user bang tu khoa
    @GetMapping("search")
    public List<User> searchUsers(@RequestParam("keyword") String keyword) {
        return userRepository.findByKeyword(keyword);
    }

    //lay ra user bang userId
    @GetMapping("/{id}")
    //let return an object with: data, message, status
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.isPresent() ?
            ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query user successfully", foundUser)
            ):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "Cannot find User with id = "+id, "")
            );
    }

    //Them user
//    @PostMapping("insert")
//    ResponseEntity<ResponseObject> insertUser(@RequestBody User newUser) {
//        List<User> foundUsers = userRepository.findByUserName(newUser.getUserName().trim());
//        if (foundUsers.size() > 0) {
//            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
//                    new ResponseObject("failed", "User name alreadly taken", "")
//            );
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("ok", "Insert User successfully", userRepository.save(newUser))
//        );
//    }

    //sua user
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody User newUser, @PathVariable Long id) {
        User updatedUser =  userRepository.findById(id)
                .map(user -> {
                    user.setUserName(newUser.getUserName());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                }).orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "update User successfully", updatedUser)
        );
    }

    //xoa user
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        boolean exists = userRepository.existsById(id);
        if (exists) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete user successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find user to delete", "")
        );
    }
}
