package com.jms.ourhomeassignment.service.order.history;

import com.jms.ourhomeassignment.data.order.history.OrderHistory;
import org.springframework.data.domain.Pageable;

public interface OrderHistoryService {
    OrderHistory getOrderHistory(String userId, Pageable pageable);
    OrderHistory getOrderHistory(Pageable pageable);
}
