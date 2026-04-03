package org.example.t10b.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.security.Principal;

import org.example.t10b.dto.Request.OrderRequest;
import org.example.t10b.dto.Response.OrderResponse;
import org.example.t10b.entity.OrderStatus;
import org.example.t10b.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/my")
    public List<OrderResponse> getMyOrders(Principal principal) {
        String login = (principal != null) ? principal.getName() : null;
        return orderService.getOrdersByClientLogin(login);
    }

    @GetMapping("/status/{status}")
    public List<OrderResponse> getOrdersByStatus(@PathVariable OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@Valid @RequestBody OrderRequest orderRequest, Principal principal) {
        String login = (principal != null) ? principal.getName() : null;
        orderService.createOrder(orderRequest, login);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam OrderStatus newStatus,
                                             Principal principal) {
        if (principal != null) {
            orderService.updateStatus(id, newStatus, principal.getName());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<Void> payOrder(@PathVariable Long id, Principal principal) {
        if (principal != null) {
            orderService.payOrder(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String currentLogin = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        orderService.deleteOrder(id, currentLogin, isAdmin);

        return ResponseEntity.noContent().build();
    }
}