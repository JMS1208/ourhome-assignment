package com.jms.ourhomeassignment.service.order.history;

import com.jms.ourhomeassignment.data.order.history.OrderHistory;
import com.jms.ourhomeassignment.entity.order.Order;
import com.jms.ourhomeassignment.repository.order.OrderJpaRepository;
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

    @Override
    public OrderHistory getOrderHistory(String userId, Pageable pageable) {

        Page<Order> orderPage = orderJpaRepository.findByUser_UserId(userId, pageable);

        List<Order> orders = orderPage.getContent();

        return new OrderHistory(new HashSet<>(orders));
    }
}
