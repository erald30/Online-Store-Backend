package com.app.services;

import com.app.dto.OrderRequest;
import com.app.dto.OrderItemDto;
import com.app.entities.Order;
import com.app.entities.OrderItem;
import com.app.entities.Product;
import com.app.repository.OrderItemRepository;
import com.app.repository.OrderRepository;
import com.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;


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
        for (OrderItemDto item : orderRequest.getOrderItems()) {
            Optional<Product> optionalProduct = productRepository.findProductsByTitleIgnoreCase(item.getProductTitle());
            if (optionalProduct.isPresent()) {
                OrderItem newOrderItem = OrderItem.builder()
                        .orderId(newOrder.getId())
                        .productTitle(item.getProductTitle())
                        .quantity(item.getQuantity())
                        .productPrice(optionalProduct.get().getPrice())
                        .productSale(optionalProduct.get().isOnSale())
                        .build();

                orderItemRepository.save(newOrderItem);
            }
            total += optionalProduct.get().getPrice() * item.getQuantity();

            productRepository.findById(UUID.fromString(item.getProductId())).ifPresent(product -> {
                product.setTotalSales(product.getTotalSales() + item.getQuantity());
                productRepository.save(product);
            });
        }
        List<OrderItem> orderItems = orderItemRepository.findAll();

        // 5. Update total on Order object and save to database
        newOrder.setOrderItems(orderItems);
        newOrder.setTotalPrice(total);
        orderRepository.save(newOrder);

        return ResponseEntity.ok(newOrder);
    }

}

