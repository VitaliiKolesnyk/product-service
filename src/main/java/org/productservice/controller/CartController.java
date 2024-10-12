package org.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.productservice.model.Cart;
import org.productservice.model.Product;
import org.productservice.service.CartService;
import org.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final ProductService productService;

    @PostMapping("/add/{userId}/{productId}/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public Cart addProductToCart(@PathVariable String userId, @PathVariable String productId, @PathVariable int quantity) {
        // Fetch the product (this can be done via ProductService)
        Product product = productService.getProductById(productId); // Implement product fetching
        return cartService.addProductToCart(userId, product, quantity);
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return cartService.getCartByUserId(userId);
    }

    @DeleteMapping("/clear/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }

    @PutMapping("/delete/{userId}/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeItemFromCart(@PathVariable String userId, @PathVariable String productId) {
        Product product = productService.getProductById(productId);

        cartService.removeItemFromCart(userId, product);
    }
}
