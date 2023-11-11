package com.app.services;

import com.app.entities.Product;
import com.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public List<Product> getProductByTittle(String title){
        return productRepository
                .findProductsByTitleIgnoreCase(title);
    }
    public ResponseEntity<?> create(Product product){
        if (product.isOnSale()){
            product.setDiscountPrice(product.getPrice()-(product.getPrice()*0.2));
        }
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<?> update(Product product){
        Product productUpdate = productRepository.findById(product.getId())
                .orElseThrow(()->new IllegalStateException
                        ("Product with id: "+product.getId()+" does not exist"));

        if (product.getTitle().equals(productUpdate.getTitle())){
            return ResponseEntity.badRequest().body("This tittle already exist.");
        }
        if (product.getTitle() == null || product.getTitle().isBlank()){
            return ResponseEntity.badRequest().body("Tittle cannot be null");
        }
        productUpdate.setBrand(product.getBrand());
        productUpdate.setColor(product.getColor());
        productUpdate.setTitle(product.getTitle());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setOnSale(product.isOnSale());
        productRepository.save(productUpdate);
        return ResponseEntity.ok("Product successfully updated");
    }

    public ResponseEntity<?> delete(UUID id){
        Product optionlaProduct = productRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Product with id: "+id+" does not exist."));
        productRepository.deleteById(optionlaProduct.getId());
        return ResponseEntity.ok("Product deleted successfully");
    }

}
