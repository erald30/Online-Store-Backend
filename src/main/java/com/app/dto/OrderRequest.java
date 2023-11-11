package com.app.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    protected List<ProductRequest> orderItems = new ArrayList<>();
}
