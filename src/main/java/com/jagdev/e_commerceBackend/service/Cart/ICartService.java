package com.jagdev.e_commerceBackend.service.Cart;

import com.jagdev.e_commerceBackend.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

}
