package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ProductCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductCategoryDto {

    private Integer id;
    private String name;
    private Integer sequence;
    private String showYn;

    private List<ProductCategoryDto> children;

    public ProductCategoryDto(final ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getName();
        this.sequence = productCategory.getSequence();
        this.showYn = productCategory.getShowYn();
        this.children = productCategory.getChildren().stream().map(ProductCategoryDto::new).collect(Collectors.toList());
    }

    public ProductCategory toEntity(ProductCategoryDto productCategoryDto) {
        return ProductCategory.builder()
                .name(productCategoryDto.getName())
                .showYn(productCategoryDto.getShowYn())
                .sequence(productCategoryDto.getSequence())
                .build();
    }
}
