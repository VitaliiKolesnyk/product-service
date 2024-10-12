package org.productservice.service;

import org.productservice.model.Cart;
import org.productservice.model.Product;

public interface CartService {

    Cart addProductToCart(String userId, Product product, int quantity);

    Cart getCartByUserId(String userId);

    void clearCart(String userId);

    void removeItemFromCart(String userId, Product product);
}
