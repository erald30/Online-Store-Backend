package com.app.controllers;

import com.app.dto.ProductFilter;
import com.app.entities.Product;
import com.app.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    public List<Product> search(ProductFilter search) {
        return productService.search(search);
    }

    @GetMapping("/all")
        public List<Product> getAll () {
            return productService.getAllProducts();
        }
    @GetMapping("/new-products")
    public List<Product> getNewProducts () {
        return productService.getLatestProducts();
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Product product){
        return ResponseEntity.ok(productService.create(product));
    }
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Product product){
        return productService.update(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id){
        return productService.delete(id);
    }
}
