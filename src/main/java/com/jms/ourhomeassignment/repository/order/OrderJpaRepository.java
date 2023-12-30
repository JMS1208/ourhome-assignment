package com.jms.ourhomeassignment.repository.order;

import com.jms.ourhomeassignment.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_UserId(String userId, Pageable pageable);
}
