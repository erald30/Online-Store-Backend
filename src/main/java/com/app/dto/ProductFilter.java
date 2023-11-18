package com.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductFilter {
    protected String query;
    protected String category;
    protected Boolean sale;
    protected String brands;
}
