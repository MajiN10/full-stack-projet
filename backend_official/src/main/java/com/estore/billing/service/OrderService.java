package com.estore.billing.service;

import com.estore.billing.entity.Order;
import com.estore.billing.entity.OrderItem;
import com.estore.billing.repository.OrderRepository;
import com.estore.inventory.entity.Inventory;
import com.estore.inventory.repository.InventoryRepository;
import com.estore.shopping.entity.Cart;
import com.estore.shopping.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final InventoryRepository inventoryRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional // Ensures that if one part fails, everything is rolled back
    public Order placeOrder(Long userId) {
        // 1. Find User's Cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot place an order with an empty cart");
        }

        // 2. Create the Order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("COMPLETED");

        double total = 0;

        // 3. Convert CartItems to OrderItems AND Update Stock
        for (var cartItem : cart.getItems()) {
            // Check stock again before finalizing
            Inventory inventory = inventoryRepository.findByProductId(cartItem.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found in inventory"));

            if (inventory.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Stock insufficient for product: " + cartItem.getProduct().getName());
            }

            // Decrease stock
            inventory.setQuantity(inventory.getQuantity() - cartItem.getQuantity());
            inventoryRepository.save(inventory);

            // Create Order Line
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            order.getItems().add(orderItem);

            total += (cartItem.getUnitPrice() * cartItem.getQuantity());
        }

        order.setTotalAmount(total);

        // 4. Clear the Cart (Requirement Page 18)
        cart.getItems().clear();
        cartRepository.save(cart);

        // 5. Save and return the final Order
        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}