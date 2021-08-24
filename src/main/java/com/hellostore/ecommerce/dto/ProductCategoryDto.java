package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ProductCategory;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductCategoryDto {

    private Integer id;
    private String name;
    private Integer sequence;

    private List<ProductCategoryDto> children;

    public ProductCategoryDto(final ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getCategoryName();
        this.sequence = productCategory.getSequence();
        this.children = productCategory.getChildren().stream().map(ProductCategoryDto::new).collect(Collectors.toList());
    }
}
