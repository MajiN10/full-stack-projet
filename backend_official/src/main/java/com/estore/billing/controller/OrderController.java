package com.estore.billing.controller;

import com.estore.billing.entity.Order;
import com.estore.billing.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1. Place Order (POST /api/orders?userId=1)
    @PostMapping
    public Order placeOrder(@RequestParam Long userId) {
        return orderService.placeOrder(userId);
    }

    // 2. Get Order History (GET /api/orders/user/1)
    @GetMapping("/user/{userId}")
    public List<Order> getHistory(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }
}