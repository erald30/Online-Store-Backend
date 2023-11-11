package com.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@Table(name = "ee_preferred_products")
public class PreferredProducts extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;

    @OneToMany(mappedBy = "preferredProducts")
    private List<Product> products;
}
