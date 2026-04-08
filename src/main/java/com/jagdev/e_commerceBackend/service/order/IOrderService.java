package com.jagdev.e_commerceBackend.service.order;

import com.jagdev.e_commerceBackend.Dto.OrderDto;
import com.jagdev.e_commerceBackend.model.Order;

import java.util.List;

public interface IOrderService {
Order placeOrder(Long userId);
OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
