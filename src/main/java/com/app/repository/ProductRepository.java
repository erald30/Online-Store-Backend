package com.app.repository;

import com.app.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>{

    List<Product> findAllById(UUID uuid);

    List<Product>findAllByCategoryIgnoreCaseAndBrandIgnoreCase(String category, String brand);

    List<Product> findProductByOnSale(Boolean onSale);

    List<Product> findProductsByTitleIgnoreCase(String description);


}
