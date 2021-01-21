package com.jpop.userservice.repository;

import com.jpop.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
