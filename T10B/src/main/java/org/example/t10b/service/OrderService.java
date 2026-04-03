package org.example.t10b.service;

import org.example.t10b.dto.Request.OrderRequest;
import org.example.t10b.dto.Response.OrderResponse;
import org.example.t10b.entity.Order;
import org.example.t10b.entity.OrderStatus;
import org.example.t10b.entity.User;
import org.example.t10b.exception.BusinessException;
import org.example.t10b.mapper.OrderMapper;
import org.example.t10b.repository.OrderRepository;
import org.example.t10b.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderResponse> getAllOrders() {
        return orderMapper.toResponseList(orderRepository.findAll());
    }

    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findAllByStatus(status).stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    public List<OrderResponse> getOrdersByClientLogin(String login) {
        return orderRepository.findAllByClientLogin(login).stream()
                .map(orderMapper::toResponse)
                .toList();
    }
    @Transactional
    public void createOrder(OrderRequest request, String login) {
        User client = userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("Client not found with login: " + login));

        Order order = new Order();
        order.setClient(client);
        order.setCapacity(request.capacity());
        order.setApartmentClass(request.apartmentClass());
        order.setStayDays(request.stayDays());
        order.setPaid(false);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }

    @Transactional
    public void updateStatus(Long orderId, OrderStatus newStatus, String adminLogin) {
        Order order = findOrderById(orderId);

        User admin = userRepository.findByLogin(adminLogin)
                .orElseThrow(() -> new BusinessException("Admin not found with login: " + adminLogin));

        order.setStatus(newStatus);
        order.setAdmin(admin);
        order.setProcessedAt(LocalDateTime.now());

        orderRepository.save(order);
    }

    @Transactional
    public void payOrder(Long orderId) {
        Order order = findOrderById(orderId);
        if (order.getStatus() != OrderStatus.CONFIRMED) {
            throw new BusinessException("Order must be CONFIRMED before payment. Current status: " + order.getStatus());
        }
        order.setPaid(true);
        order.setStatus(OrderStatus.PAID);

        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new BusinessException("User is not authenticated");
        }

        final String currentLogin = auth.getName();
        final boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found"));

        if (!isAdmin && !order.getClient().getLogin().equals(currentLogin)) {
            throw new BusinessException("Access denied: You can only delete your own orders");
        }

        orderRepository.delete(order);
    }
    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found with id: " + id));
    }
}