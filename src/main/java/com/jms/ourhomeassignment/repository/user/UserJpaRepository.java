package com.jms.ourhomeassignment.repository.user;

import com.jms.ourhomeassignment.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);
}
