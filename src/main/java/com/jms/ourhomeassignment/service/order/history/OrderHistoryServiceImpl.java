package com.jms.ourhomeassignment.service.order.history;

import com.jms.ourhomeassignment.data.order.history.OrderHistory;
import com.jms.ourhomeassignment.entity.order.Order;
import com.jms.ourhomeassignment.repository.order.OrderJpaRepository;
import com.jms.ourhomeassignment.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderJpaRepository orderJpaRepository;

    private final AuthService authService;

    private final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryServiceImpl.class);

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

        LOGGER.info("[getOrderHistory] : {}", orders.size());

        return new OrderHistory(new HashSet<>(orders));
    }
}
