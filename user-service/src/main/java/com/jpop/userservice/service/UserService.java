package com.jpop.userservice.service;

import com.jpop.userservice.model.UserRequest;
import com.jpop.userservice.model.UserDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Service to handle user details request
 */
public interface UserService {
    /**
     * Get user details
     * @param userId i.e. user id
     * @return UserDTO i.e. use details
     */
    UserDTO getUserDetails(@NotNull(message = "user id cannot be null") Integer userId);

    /**
     * Get all User Details.
     * @return List<UserDTO> i.e. list of users with details.
     */
    List<UserDTO> getAllUsers();

    /**
     * Create a user for given below request.
     * @param loggedIn i.e. logged in
     * @param userRequest i.e. user request
     * @return UserDTO i.e. returns newly created user.
     */
    UserDTO createUser(@NotNull(message = "user id cannot be null") Integer loggedIn,
                       @NotNull(message = "user request cannot be null") UserRequest
                               userRequest);

    /**
     * Update a user for below request.
     * @param loggedIn i.e. logged in user.
     * @param userId i.e. user id of user details to be updated.
     * @param userRequest i.e. user request.
     * @return UserDTO i.e. returns updated user.
     */
    UserDTO updateUser(@NotNull(message = "logged in cannot be null") Integer loggedIn,
                       @NotNull(message = "user id cannot be null") Integer userId,
                       @NotNull(message = "user request cannot be null") UserRequest
                               userRequest);

    /**
     * Delete a user with given user id
     * @param userId i.e. user id.
     */
    void deleteUser(@NotNull(message = "user id cannot be null") Integer userId);
}
