package com.jpop.userservice.controller;

import com.jpop.userservice.model.UserRequest;
import com.jpop.userservice.model.UserResponse;
import com.jpop.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@Slf4j
@EnableSwagger2
@RequestMapping("v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users/{user_id}")
    public ResponseEntity<UserResponse> getUserDetails(
            @PathVariable(value = "user_id") Integer userId) {
        UserResponse getUserServiceResponse = userService.getUserDetails(userId);
        return new ResponseEntity<>(getUserServiceResponse, HttpStatus.OK);
    }

    @GetMapping("users/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> getAllUserServiceResponse = userService.getAllUsers();
        return new ResponseEntity<>(getAllUserServiceResponse, HttpStatus.OK);
    }

    @PostMapping("users")
    public ResponseEntity<UserResponse> addUser(
            @RequestParam(value = "logged_in") Integer loggedIn,
            @RequestBody UserRequest userRequest) {
        UserResponse addUserServiceResponse =
                userService.addUser(loggedIn, userRequest);
        return new ResponseEntity<>(addUserServiceResponse, HttpStatus.OK);
    }

    @PutMapping("users/{user_id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable(value = "user_id") Integer userId,
            @RequestParam(value = "logged_in") Integer loggedIn,
            @RequestBody UserRequest userRequest) {
        UserResponse updateUserServiceResponse =
                userService.updateUser(loggedIn, userId, userRequest);
        return new ResponseEntity<>(updateUserServiceResponse, HttpStatus.OK);
    }

    @DeleteMapping("users/{user_id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable(value = "user_id") Integer userId) {
        Boolean deleteUserServiceResponse = userService.deleteUser(userId);
        return new ResponseEntity<>(deleteUserServiceResponse, HttpStatus.OK);
    }
}
