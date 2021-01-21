package com.jpop.userservice.controller;

import com.jpop.userservice.model.UserRequest;
import com.jpop.userservice.model.UserDTO;
import com.jpop.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for handling user request
 */
@RestController
@Slf4j
@EnableSwagger2
@RequestMapping("v1/")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * {@code GET  users/{user_id}} : get the user details for the requested user id.
     *
     * @param userId i.e. user id
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the UserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("users/{user_id}")
    public ResponseEntity<UserDTO> getUserDetails(
            @PathVariable(value = "user_id") Integer userId) {
        log.debug("REST request to get user details with id:{}", userId);
        UserDTO getUserServiceResponse = userService.getUserDetails(userId);
        return new ResponseEntity<>(getUserServiceResponse, HttpStatus.OK);
    }

    /**
     * {@code GET  users/} : get the all the users details.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the list of UserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("users/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.debug("REST request to get all user details");
        List<UserDTO> getAllUserServiceResponse = userService.getAllUsers();
        return new ResponseEntity<>(getAllUserServiceResponse, HttpStatus.OK);
    }

    /**
     * {@code POST  users} : Create a new User.
     *
     * @param userRequest the user request to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new UserDTO, or with status {@code 500 (Internal Server Error)} if the
     *         user has already an ID.
     */
    @PostMapping("users")
    public ResponseEntity<UserDTO> createUser(
            @RequestParam(value = "logged_in") Integer loggedIn,
            @Valid @RequestBody UserRequest userRequest) {
        log.debug("REST request by user :{} to create user for request:{}", loggedIn, userRequest);
        UserDTO addUserServiceResponse =
                userService.createUser(loggedIn, userRequest);
        return new ResponseEntity<>(addUserServiceResponse, HttpStatus.OK);
    }

    /**
     * {@code PUT  users/{user_id}} : Update existing User Details.
     *
     * @param userRequest the user request to update.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new UserDTO, or with status {@code 500 (Internal Server Error)} if the
     *         user does not exist with given id.
     */
    @PutMapping("users/{user_id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable(value = "user_id") Integer userId,
            @RequestParam(value = "logged_in") Integer loggedIn,
            @Valid @RequestBody UserRequest userRequest) {
        log.debug("REST request by user: {} to update user: {} for request: {}", loggedIn, userId,
                userRequest);
        UserDTO updateUserServiceResponse =
                userService.updateUser(loggedIn, userId, userRequest);
        return new ResponseEntity<>(updateUserServiceResponse, HttpStatus.OK);
    }

    /**
     * {@code DELETE  users/{user_id}} : delete the "id" user.
     *
     * @param userId the id of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 200}.
     */
    @DeleteMapping("users/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "user_id") Integer userId) {
        log.debug("REST request to delete user details with id:{}", userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
