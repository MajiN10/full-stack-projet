package com.estore.shopping.controller;

import com.estore.shopping.entity.Cart;
import com.estore.shopping.entity.CartItem;
import com.estore.shopping.repository.CartItemRepository;
import com.estore.shopping.repository.CartRepository;
import com.estore.shopping.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartController(CartService cartService,
                          CartRepository cartRepository,
                          CartItemRepository cartItemRepository) {
        this.cartService = cartService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartRepository.findByUserId(userId).orElse(new Cart());
    }

    @PostMapping("/add")
    public Cart addItem(@RequestParam Long userId,
                        @RequestParam Long productId,
                        @RequestParam int quantity) {
        return cartService.addToCart(userId, productId, quantity);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateItem(@RequestParam Long userId,
                                        @RequestParam Long itemId,
                                        @RequestParam int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        for (CartItem item : cart.getItems()) {
            if (item.getId().equals(itemId)) {
                if (quantity <= 0) {
                    cart.getItems().remove(item);
                } else {
                    item.setQuantity(quantity);
                }
                cartRepository.save(cart);
                return ResponseEntity.ok(cart);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeItem(@PathVariable Long itemId,
                                        @RequestParam Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        boolean removed = cart.getItems().removeIf(item -> item.getId().equals(itemId));
        if (removed) {
            cartRepository.save(cart);
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.notFound().build();
    }
}
