package com.jagdev.e_commerceBackend.service.order;


import com.jagdev.e_commerceBackend.enums.OrderStatus;
import com.jagdev.e_commerceBackend.exception.ResourceNotFoundException;
import com.jagdev.e_commerceBackend.model.Cart;
import com.jagdev.e_commerceBackend.model.Order;
import com.jagdev.e_commerceBackend.model.OrderItem;
import com.jagdev.e_commerceBackend.model.Product;
import com.jagdev.e_commerceBackend.repository.OrderRepository;
import com.jagdev.e_commerceBackend.repository.ProductRepository;
import com.jagdev.e_commerceBackend.service.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;


    @Override
    public Order placeOrder(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);
        Order order =createOrder(cart);
        List<OrderItem> orderItemList =createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder=orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem ->{
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory()- cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );

        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(item -> item.getPrice()
                .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
