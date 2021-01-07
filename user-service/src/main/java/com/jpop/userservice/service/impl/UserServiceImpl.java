package com.jpop.userservice.service.impl;

import com.jpop.userservice.constant.UserStatusCode;
import com.jpop.userservice.entity.User;
import com.jpop.userservice.exception.UserServiceBaseException;
import com.jpop.userservice.model.UserRequest;
import com.jpop.userservice.model.UserResponse;
import com.jpop.userservice.model.UserServiceResponse;
import com.jpop.userservice.repository.UserRepository;
import com.jpop.userservice.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
    public UserServiceResponse<UserResponse> getUserDetails(Integer userId) {
        UserServiceResponse userServiceResponse = new UserServiceResponse(UserStatusCode.FAILED);
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            UserResponse user = new UserResponse();
            BeanUtils.copyProperties(userOptional.get() , user);
            userServiceResponse.setResponseObject(user);
            userServiceResponse.setStatus(UserStatusCode.SUCCESS);
        } else {
            throw new UserServiceBaseException(UserStatusCode.DATABASE_ERROR.getDesc());
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse<Page<UserResponse>> getAllUsers(Pageable pageable) {
        UserServiceResponse userServiceResponse = new UserServiceResponse(UserStatusCode.FAILED);
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserResponse> userResponseList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(userPage.getContent())){
            userPage.getContent().forEach( user -> {
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(user , userResponse);
                userResponseList.add(userResponse);
            });
        }
        userServiceResponse.setResponseObject(new PageImpl<>(userResponseList, pageable, userResponseList.size() ));
        userServiceResponse.setStatus(UserStatusCode.SUCCESS);
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse<UserResponse> addUser(Integer loggedIn, UserRequest userRequest) {
        UserServiceResponse userServiceResponse = new UserServiceResponse(UserStatusCode.FAILED);
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        user.setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()));
        user.setCreatedBy(loggedIn);
        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(savedUser, userResponse);
        userServiceResponse.setResponseObject(userResponse);
        userServiceResponse.setStatus(UserStatusCode.SUCCESS);
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse<UserResponse> updateUser(@NonNull Integer loggedIn, Integer userId, UserRequest userRequest) {
        UserServiceResponse userServiceResponse = new UserServiceResponse(UserStatusCode.FAILED);
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            BeanUtils.copyProperties(userRequest, user);
            user.setUpdatedBy(loggedIn);
            userServiceResponse.setResponseObject(userRepository.save(user));
            userServiceResponse.setStatus(UserStatusCode.SUCCESS);
        } else {
            throw new UserServiceBaseException(UserStatusCode.DATABASE_ERROR.getDesc());
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse<Boolean> deleteUser(Integer userId) {
        UserServiceResponse bookResponse = new UserServiceResponse(Boolean.FALSE, UserStatusCode.FAILED);
        userRepository.deleteById(userId);
        bookResponse.setResponseObject(Boolean.TRUE);
        bookResponse.setStatus(UserStatusCode.SUCCESS);
        return bookResponse;
    }
}
