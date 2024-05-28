package com.cts.agrofarmingstore.controller;


import com.cts.agrofarmingstore.model.User;
import com.cts.agrofarmingstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serviceImpl.userServiceImpl;

import java.util.List;
// Controller class for handling user related requests

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/users")
public class UserController {

    // creating instance of userServiceImpl interface
    @Autowired
    private userServiceImpl userService;

    // fetching all Users
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> loginList = userService.getAllUsers();
        return new ResponseEntity<>(loginList, HttpStatus.OK);
    }

    //fetching User by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    adding User to the database
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User userObj) {
        User user = userService.addUser(userObj);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //deleting User from our database
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    //updating User in our database
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User userObj) {
        User user = userService.updateUser(userObj);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    logging user in the web app
    @GetMapping("/{email}/{password}")
    public User logIn(@PathVariable String email, @PathVariable String password) {
        User user = userService.logIn(email, password);
        return user;
    }


}
