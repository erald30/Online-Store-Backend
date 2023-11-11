package com.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "ee_user_product")
public class UserProduct extends BaseEntity {
    @Column(name = "product_id")
    protected UUID productId;

    @Column(name = "user_id")
    protected UUID userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    protected User user;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    protected Product product;

}
