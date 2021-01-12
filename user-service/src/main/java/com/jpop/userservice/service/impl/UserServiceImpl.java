package com.jpop.userservice.service.impl;

import com.jpop.userservice.constant.UserStatusCode;
import com.jpop.userservice.entity.User;
import com.jpop.userservice.exception.UserServiceBaseException;
import com.jpop.userservice.model.UserRequest;
import com.jpop.userservice.model.UserResponse;
import com.jpop.userservice.repository.UserRepository;
import com.jpop.userservice.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserResponse getUserDetails(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        UserResponse userResponse;
        if(userOptional.isPresent()){
            userResponse = new UserResponse();
            BeanUtils.copyProperties(userOptional.get() , userResponse);
        } else {
            throw new UserServiceBaseException(UserStatusCode.DATABASE_ERROR.getDesc());
        }
        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUsers(Pageable pageable) {
        List<User> userPage = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        userPage.forEach( user -> {
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user , userResponse);
            userResponseList.add(userResponse);
        });
        return userResponseList;
    }

    @Override
    public UserResponse addUser(Integer loggedIn, UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        user.setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()));
        user.setCreatedBy(loggedIn);
        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(savedUser, userResponse);
        return userResponse;
    }

    @Override
    public UserResponse updateUser(@NonNull Integer loggedIn, Integer userId, UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findById(userId);
        UserResponse userResponse = new UserResponse();
        if(userOptional.isPresent()){
            User user = userOptional.get();
            BeanUtils.copyProperties(userRequest, user);
            user.setUpdatedBy(loggedIn);
            BeanUtils.copyProperties(userRepository.save(user), userResponse);
        } else {
            throw new UserServiceBaseException(UserStatusCode.DATABASE_ERROR.getDesc());
        }
        return userResponse;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        return true;
    }
}
