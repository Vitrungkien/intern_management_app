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
    //DI = Dependency Injection
    @Autowired
    private UserRepository repository;
    @GetMapping("")
    //this request is: http://localhost:8080/api/v1/Users
    List<User> getAllUser() {
        return repository.findAll();
    }

    @GetMapping("search")
    public List<User> searchUsers(@RequestParam("keyword") String keyword) {
        return repository.findByKeyword(keyword);
    }

    //Get detail user
    @GetMapping("/{id}")
    //let return an object with: data, message, status
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<User> foundUser = repository.findById(id);
        return foundUser.isPresent() ?
            ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query user successfully", foundUser)
            ):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "Cannot find User with id = "+id, "")
            );
    }

    @PostMapping("insert")
    ResponseEntity<ResponseObject> insertUser(@RequestBody User newUser) {
        List<User> foundUsers = repository.findByUsername(newUser.getUsername().trim());
        if (foundUsers.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "User name alreadly taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert User successfully", repository.save(newUser))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody User newUser, @PathVariable Long id) {
        User updatedUser =  repository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    return repository.save(user);
                }).orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "update User successfully", updatedUser)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete user successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find user to delete", "")
        );
    }
}
