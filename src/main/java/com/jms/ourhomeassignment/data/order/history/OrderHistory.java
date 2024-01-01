package com.jms.ourhomeassignment.data.order.history;

import com.jms.ourhomeassignment.entity.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class OrderHistory {
    private Set<Order> orders;
}
