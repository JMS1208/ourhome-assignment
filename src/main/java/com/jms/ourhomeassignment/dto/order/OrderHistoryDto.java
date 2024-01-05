package com.jms.ourhomeassignment.dto.order;

import com.jms.ourhomeassignment.data.order.history.OrderHistory;
import com.jms.ourhomeassignment.entity.order.Order;
import com.jms.ourhomeassignment.entity.order.detail.OrderDetail;
import com.jms.ourhomeassignment.entity.product.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderHistoryDto {
    private List<OrderDto> orders;

    public static OrderHistoryDto from(OrderHistory orderHistory) {
        List<OrderDto> orderDtos = orderHistory.getOrders().stream().sorted(((o1, o2) -> -o1.getOrderedAt().compareTo(o2.getOrderedAt()))).map(OrderDto::from).toList();
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        orderHistoryDto.setOrders(orderDtos);
        return orderHistoryDto;
    }

    @Data
    @Builder
    public static class OrderDto {
        private Long id;
        private LocalDateTime orderedAt;
        private List<OrderDetailDto> orderDetails;

        public static OrderDto from(Order order) {

            List<OrderDetailDto> orderDetailDtos
                    = order.getOrderDetails().stream()
                    .map(OrderDetailDto::from).collect(Collectors.toList());

            return OrderDto.builder()
                    .id(order.getId())
                    .orderedAt(order.getOrderedAt())
                    .orderDetails(orderDetailDtos)
                    .build();
        }

        @Data
        @Builder
        public static class OrderDetailDto {
            private Long id;
            private ProductDto product;
            private int quantity;
            private long price;


            public static OrderDetailDto from(OrderDetail orderDetail) {
                ProductDto productDto = ProductDto.from(orderDetail.getProduct());

                return OrderDetailDto.builder()
                        .id(orderDetail.getId())
                        .product(productDto)
                        .quantity(orderDetail.getQuantity())
                        .price(orderDetail.getPrice())
                        .build();
            }

            @Data
            @Builder
            public static class ProductDto {
                private Long id;
                private String name;
                private int price;

                public static ProductDto from(Product product) {
                    return ProductDto.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .build();
                }
            }
        }
    }
}
