package com.app.repository;

import com.app.entities.OrderItem;
import com.app.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID>{

    @Query("from OrderItem o order by o.quantity desc limit 4")
    List<OrderItem> findMostSoldProducts();
    List<OrderItem> findAllByOrderId (UUID orderId);

    List<OrderItem> findAllByProductTitleContainingIgnoreCase(String title);





}
