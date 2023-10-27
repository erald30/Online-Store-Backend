package com.app.repository;

import com.app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>{

    @Query(" from Order o where o.createdAt = :createdAt")
    List<Order> getOrdersInDate(@Param("createdAt") Instant createdAt);

    List<Order> findAllByCreatedAtEquals(Instant createdAt);

}
