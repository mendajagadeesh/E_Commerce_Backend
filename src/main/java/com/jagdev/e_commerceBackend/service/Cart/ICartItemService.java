package com.jagdev.e_commerceBackend.service.Cart;

import com.jagdev.e_commerceBackend.model.CartItem;

public interface ICartItemService {
    void addItem(Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
