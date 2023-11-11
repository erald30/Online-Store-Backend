package com.app.services;

import com.app.dto.CategoryRead;
import com.app.entities.Category;
import com.app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(categoryRepository.findAll().stream().map(o -> CategoryRead.builder().id(o.getId()).title(o.getTitle()).build()));
    }

    public void createDefaultCategories() {
        categoryRepository.findByTitleIgnoreCase("Smartphones").orElseGet(() -> {
            Category item = Category.builder().title("Smartphones").build();
            categoryRepository.save(Category.builder().title("Smartphones").build());
            log.info("Category Smartphones created");
            return item;
        });
        categoryRepository.findByTitleIgnoreCase("TV-s").orElseGet(() -> {
            Category item = Category.builder().title("TV-s").build();
            categoryRepository.save(Category.builder().title("TV-s").build());
            log.info("Category TV-s created");
            return item;
        });
        categoryRepository.findByTitleIgnoreCase("Laptop").orElseGet(() -> {
            Category item = Category.builder().title("Laptop").build();
            categoryRepository.save(Category.builder().title("Laptop").build());
            log.info("Category Laptop created");
            return item;
        });
        categoryRepository.findByTitleIgnoreCase("PC-s").orElseGet(() -> {
            Category item = Category.builder().title("PC-s").build();
            categoryRepository.save(Category.builder().title("PC-s").build());
            log.info("Category PC-s created");
            return item;
        });
        categoryRepository.findByTitleIgnoreCase("Accessories").orElseGet(() -> {
            Category item = Category.builder().title("Accessories").build();
            categoryRepository.save(Category.builder().title("Accessories").build());
            log.info("Category Accessories created");
            return item;
        });
        categoryRepository.findByTitleIgnoreCase("Home electronics").orElseGet(() -> {
            Category item = Category.builder().title("Home electronics").build();
            categoryRepository.save(Category.builder().title("Home electronics").build());
            log.info("Category Home electronics created");
            return item;
        });
    }
}
