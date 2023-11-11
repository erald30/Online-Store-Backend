package com.app.services;

import com.app.dto.OrderRequest;
import com.app.dto.ProductRequest;
import com.app.entities.Order;
import com.app.entities.OrderItem;
import com.app.repository.OrderItemRepository;
import com.app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    public List<Order> getList() {
        return orderRepository.findAll();
    }

    public ResponseEntity<?> create(OrderRequest orderRequest) {
        if (orderRequest == null)
            return ResponseEntity.badRequest().body("Order object is null");
        if (orderRequest.getOrderItems().isEmpty())
            return ResponseEntity.badRequest().body("Order should contain at least 1 product!");

        Order newOrder = new Order();

        orderRepository.save(newOrder);


        double total = 0;
        for (ProductRequest item : orderRequest.getOrderItems()) {
            OrderItem newOrderItem = OrderItem.builder()
                    .orderId(newOrder.getId())
                    .productTitle(item.getProductTitle())
                    .quantity(item.getQuantity())
                    .productPrice(item.getProductPrice())
                    //   .total(item.getProductPrice() * item.getQuantity())
                    .build();
            orderItemRepository.save(newOrderItem);

            total += item.getProductPrice() * item.getQuantity();
        }
        List<OrderItem> orderItems = orderItemRepository.findAll();

        // 5. Update total on Order object and save to database
        newOrder.setOrderItems(orderItems);
        newOrder.setTotalPrice(total);
        orderRepository.save(newOrder);

        return ResponseEntity.ok(newOrder);
    }

}

