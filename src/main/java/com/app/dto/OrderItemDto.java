package com.app.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    protected String productId;
    protected String productTitle;
    protected double productPrice;
    protected int quantity;
}
