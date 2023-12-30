package com.jms.ourhomeassignment.repository.user;

import com.jms.ourhomeassignment.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
}
