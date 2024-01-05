package com.jms.ourhomeassignment.repository.order;

import com.jms.ourhomeassignment.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    @Query("select distinct o from Order o left join o.orderDetails")
    Page<Order> findByUser_UserId(String userId, Pageable pageable);

}
