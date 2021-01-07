package com.jpop.userservice.controller;

import com.jpop.userservice.entity.User;
import com.jpop.userservice.model.UserRequest;
import com.jpop.userservice.model.UserResponse;
import com.jpop.userservice.model.UserServiceResponse;
import com.jpop.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users/{user_id}")
    public ResponseEntity<UserServiceResponse<UserResponse>> getUserDetails(
            @PathVariable(value = "user_id") Integer userId) {
        UserServiceResponse<UserResponse> getUserServiceResponse = userService.getUserDetails(userId);
        return new ResponseEntity<>(getUserServiceResponse, HttpStatus.OK);
    }

    @GetMapping("users/")
    public ResponseEntity<UserServiceResponse<Page<UserResponse>>> getAllUsers(Pageable pageable) {
        UserServiceResponse<Page<UserResponse>> getAllUserServiceResponse = userService.getAllUsers(pageable);
        return new ResponseEntity<>(getAllUserServiceResponse, HttpStatus.OK);
    }

    @PostMapping("users")
    public ResponseEntity<UserServiceResponse<UserResponse>> addUser(
            @RequestParam(value = "logged_in") Integer loggedIn,
            @RequestBody UserRequest userRequest) {
        UserServiceResponse<UserResponse> addUserServiceResponse =
                userService.addUser(loggedIn, userRequest);
        return new ResponseEntity<>(addUserServiceResponse, HttpStatus.OK);
    }

    @PutMapping("users/{user_id}")
    public ResponseEntity<UserServiceResponse<UserResponse>> updateUser(
            @PathVariable(value = "user_id") Integer userId,
            @RequestParam(value = "logged_in") Integer loggedIn,
            @RequestBody UserRequest userRequest) {
        UserServiceResponse<UserResponse> updateUserServiceResponse =
                userService.updateUser(loggedIn, userId, userRequest);
        return new ResponseEntity<>(updateUserServiceResponse, HttpStatus.OK);
    }

    @DeleteMapping("users/{user_id}")
    public ResponseEntity<UserServiceResponse<Boolean>> deleteUser(
            @PathVariable(value = "user_id") Integer userId) {
        UserServiceResponse<Boolean> deleteUserServiceResponse = userService.deleteUser(userId);
        return new ResponseEntity<>(deleteUserServiceResponse, HttpStatus.OK);
    }
}
