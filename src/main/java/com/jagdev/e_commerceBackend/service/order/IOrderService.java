package com.jagdev.e_commerceBackend.service.order;

import com.jagdev.e_commerceBackend.model.Order;

import java.util.List;

public interface IOrderService {
Order placeOrder(Long userId);
Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
