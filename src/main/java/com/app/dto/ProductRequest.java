package com.app.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    protected Long productId;
    protected String productTitle;
    protected double productPrice;
    protected int quantity;
}
