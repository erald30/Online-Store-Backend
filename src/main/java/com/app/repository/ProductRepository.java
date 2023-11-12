package com.app.repository;

import com.app.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>{

    List<Product> findAllById(UUID uuid);

    List<Product> findProductByOnSale(Boolean onSale);

    Optional<Product> findProductsByTitleIgnoreCase(String description);

    @Query("from Product order by createdAt desc limit 5")
    List<Product> findLatest();



}
