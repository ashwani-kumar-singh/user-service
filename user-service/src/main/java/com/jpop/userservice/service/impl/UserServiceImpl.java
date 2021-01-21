package com.jpop.userservice.service.impl;

import com.jpop.userservice.constant.UserStatusCode;
import com.jpop.userservice.entity.User;
import com.jpop.userservice.exception.UserServiceBaseException;
import com.jpop.userservice.model.UserRequest;
import com.jpop.userservice.model.UserDTO;
import com.jpop.userservice.repository.UserRepository;
import com.jpop.userservice.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Validated
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDTO getUserDetails(Integer userId) {
        log.debug("Request to get User details for user id : {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);
        UserDTO userResponse;
        if(userOptional.isPresent()){
            userResponse = new UserDTO();
            BeanUtils.copyProperties(userOptional.get() , userResponse);
        } else {
            throw new UserServiceBaseException(UserStatusCode.DATABASE_ERROR.getDesc());
        }
        return userResponse;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.debug("Request to get all User details");
        List<User> userPage = userRepository.findAll();
        List<UserDTO> userResponseList = new ArrayList<>();
        userPage.forEach( user -> {
            UserDTO userResponse = new UserDTO();
            BeanUtils.copyProperties(user , userResponse);
            userResponseList.add(userResponse);
        });
        return userResponseList;
    }

    @Override
    public UserDTO createUser(Integer loggedIn, UserRequest userRequest) {
        log.debug("Request to save User details for request: {} by user :{}", userRequest,
                loggedIn);
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        user.setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()));
        user.setCreatedBy(loggedIn);
        User savedUser = userRepository.save(user);
        UserDTO userResponse = new UserDTO();
        BeanUtils.copyProperties(savedUser, userResponse);
        return userResponse;
    }

    @Override
    public UserDTO updateUser(@NonNull Integer loggedIn, Integer userId, UserRequest userRequest) {
        log.debug("Request to update User details for user id:{} with update request: {} by user :{}",
                userId, userRequest, loggedIn);
        Optional<User> userOptional = userRepository.findById(userId);
        UserDTO userResponse = new UserDTO();
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
    public void deleteUser(Integer userId) {
        log.debug("Request to delete User details for user id : {}", userId);
        userRepository.deleteById(userId);
    }
}
