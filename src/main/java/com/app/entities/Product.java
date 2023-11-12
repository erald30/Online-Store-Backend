package com.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ee_product")
public class Product extends BaseEntity {

    @Column(name = "brand")
    private String brand;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "banner_image")
    private String bannerImage;

    @Column(name = "product_image_1")
    private String  productImage1;

    @Column(name = "product_image_2")
    private String  productImage2;
    
    @Column(name = "product_image_3")
    private String  productImage3;

    @Column(name = "price")
    private double price;

    @Column(name = "discount_price")
    private double discountPrice;

    @Column(name = "total_sales")
    private int totalSales;

    @Column(name = "stock")
    private int stock;

    @Column(name = "on_sale")
    private boolean onSale;

    @Column(name = "category_id")
    protected UUID categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    protected Category category;
}
