package com.jpop.userservice.service;

import com.jpop.userservice.model.UserRequest;
import com.jpop.userservice.model.UserResponse;
import com.jpop.userservice.model.UserServiceResponse;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserServiceResponse<UserResponse> getUserDetails(@NonNull Integer userId);

    UserServiceResponse<Page<UserResponse>> getAllUsers(Pageable pageable);

    UserServiceResponse<UserResponse> addUser(@NonNull Integer loggedIn, @NonNull UserRequest userRequest);

    UserServiceResponse<UserResponse> updateUser(@NonNull Integer loggedIn, @NonNull Integer userId, UserRequest userRequest);

    UserServiceResponse<Boolean> deleteUser(Integer userId);
}
