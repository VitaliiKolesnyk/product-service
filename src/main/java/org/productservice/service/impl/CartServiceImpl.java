package org.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.productservice.model.Cart;
import org.productservice.model.CartItem;
import org.productservice.model.Product;
import org.productservice.repository.CartRepository;
import org.productservice.service.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public Cart addProductToCart(String userId, Product product, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart = optionalCart.orElse(new Cart(userId, new ArrayList<>()));

        // Check if the product already exists in the cart
        Optional<CartItem> existingItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.calculateTotalPrice();
        } else {
            CartItem newItem = new CartItem(product, quantity, product.getPrice() * quantity);
            cart.getCartItems().add(newItem);
        }

        cart.calculateTotalPrice();

        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId).orElse(new Cart(userId, new ArrayList<>()));
    }

    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(String userId, Product product) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cart.getCartItems();

        // Find the CartItem with the matching product and remove it
        cartItems.removeIf(cartItem -> cartItem.getProduct().equals(product));

        cart.calculateTotalPrice();

        // Optionally update the cart in the repository
        cartRepository.save(cart);
    }
}
