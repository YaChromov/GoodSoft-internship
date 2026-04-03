package org.example.t10b.repository;

import org.example.t10b.entity.Order;
import org.example.t10b.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByClientLogin(String login);
    List<Order> findAllByStatus(OrderStatus status);
}