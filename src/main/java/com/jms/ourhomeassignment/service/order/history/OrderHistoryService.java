package com.jms.ourhomeassignment.service.order.history;

import com.jms.ourhomeassignment.data.order.history.OrderHistory;
import com.jms.ourhomeassignment.entity.product.Product;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface OrderHistoryService {
    OrderHistory getOrderHistory(String userId, Pageable pageable);
}
