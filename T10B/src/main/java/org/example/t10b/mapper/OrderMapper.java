package org.example.t10b.mapper;

import org.example.t10b.dto.Response.OrderResponse;
import org.example.t10b.entity.Order;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class OrderMapper {


    public OrderResponse toResponse(Order order) {
        if (order == null) return null;

        return new OrderResponse(
                order.getId(),
                order.getClient().getLogin(),
                order.getAdmin() != null ? order.getAdmin().getLogin() : null,
                order.getCapacity(),
                order.getApartmentClass(),
                order.getStayDays(),
                order.getStatus().name(),
                order.isPaid(),
                order.getCreatedAt(),
                order.getProcessedAt()
        );
    }


    public List<OrderResponse> toResponseList(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return Collections.emptyList();
        }

        return orders.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
