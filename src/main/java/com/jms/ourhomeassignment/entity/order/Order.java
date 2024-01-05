package com.jms.ourhomeassignment.entity.order;

import com.jms.ourhomeassignment.entity.order.detail.OrderDetail;
import com.jms.ourhomeassignment.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;


}
