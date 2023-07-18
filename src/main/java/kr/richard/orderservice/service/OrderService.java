package kr.richard.orderservice.service;

import kr.richard.orderservice.dto.OrderDto;
import kr.richard.orderservice.jpa.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrderByUserId(String userId);
}
