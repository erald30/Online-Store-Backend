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

    @Query("from Product order by createdAt desc limit 4")
    List<Product> findLatest();


    @Query("""
        from Product p left join fetch p.category c where 
        (:query is null or lower(p.title) like lower(concat('%', lower(:query) , '%'))) 
        and (
        :category is null or lower(c.title) like lower(concat('%', lower(:category), '%'))
        ) 
        and (
        :sale is null or p.onSale = :sale
        ) 
        and (
        :brands is null or p.brand in (:brands)
        ) 
    """)
    List<Product> searchProductsByQueryAndCategory(
            @Param("query") String query,
            @Param("category") String category,
            @Param("sale") Boolean sale,
            @Param("brands") List<String> brands
    );
}
