package com.jpop.userservice.service;

import com.jpop.userservice.model.UserRequest;
import com.jpop.userservice.model.UserResponse;
import lombok.NonNull;

import java.util.List;

public interface UserService {
    UserResponse getUserDetails(@NonNull Integer userId);

    List<UserResponse> getAllUsers();

    UserResponse addUser(@NonNull Integer loggedIn, @NonNull UserRequest userRequest);

    UserResponse updateUser(@NonNull Integer loggedIn, @NonNull Integer userId, UserRequest userRequest);

    boolean deleteUser(Integer userId);
}
