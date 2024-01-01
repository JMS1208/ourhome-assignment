package com.jms.ourhomeassignment.service.order.history;

import com.jms.ourhomeassignment.data.order.history.OrderHistory;
import com.jms.ourhomeassignment.entity.order.Order;
import com.jms.ourhomeassignment.repository.order.OrderJpaRepository;
import com.jms.ourhomeassignment.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderJpaRepository orderJpaRepository;

    private final AuthService authService;

    @Override
    public OrderHistory getOrderHistory(String userId, Pageable pageable) {

        Page<Order> orderPage = orderJpaRepository.findByUser_UserId(userId, pageable);

        List<Order> orders = orderPage.getContent();

        return new OrderHistory(new HashSet<>(orders));
    }

    @Override
    public OrderHistory getOrderHistory(Pageable pageable) {

        String userId = authService.getCurrentUserId();

        Page<Order> orderPage = orderJpaRepository.findByUser_UserId(userId, pageable);

        List<Order> orders = orderPage.getContent();

        return new OrderHistory(new HashSet<>(orders));
    }
}
