package com.jagdev.e_commerceBackend.service.Cart;

import com.jagdev.e_commerceBackend.exception.ResourceNotFoundException;
import com.jagdev.e_commerceBackend.model.Cart;
import com.jagdev.e_commerceBackend.model.CartItem;
import com.jagdev.e_commerceBackend.model.Product;
import com.jagdev.e_commerceBackend.repository.CartItemRepository;
import com.jagdev.e_commerceBackend.repository.CartRepository;
import com.jagdev.e_commerceBackend.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService  implements ICartItemService{

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final IProductService productService;

    private final ICartService cartService;


    @Override
    public void addItem(Long cartId, Long productId, int quantity) {
       //1.get the cart
        //2.get the product
        //3.check if the product already in the cart
        //4.if yes, then increase the quantity with the requested quantity
        //5.if no ,then initiate a new cartItem entry

        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(productId);
        CartItem cartItem=cart.getCartItems().stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if(cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
       Cart cart=cartService.getCart(cartId);
       CartItem itemToRemove=getCartItem(cartId,productId);
       cart.removeItem(itemToRemove);
       cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
         Cart cart=cartService.getCart(cartId);
         cart.getCartItems().stream().
                 filter(item->item.getProduct().getId().equals(productId))
                 .findFirst().ifPresent(item->{
                     item.setQuantity(quantity);
                     item.setUnitPrice(item.getProduct().getPrice());
                     item.setTotalPrice();
                 });
         BigDecimal totalAmount=cart.getTotalAmount();
         cart.setTotalAmount(totalAmount);
         cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart=cartService.getCart(cartId);
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId()
                        .equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
