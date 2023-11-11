package com.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ee_order_item")
public class OrderItem extends BaseEntity {

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "product_sale")
    private boolean productSale;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @Column(name = "order_id")
    private UUID orderId;
}
