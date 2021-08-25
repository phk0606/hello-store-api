package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ProductCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@ToString
@Slf4j
public class ProductCategoryDto {

    private Integer id;
    private String name;
    @Setter
    private Integer sequence;
    private String showYn;
    private Integer parentId;
    private String parentName;

    private List<ProductCategoryDto> children;

    public ProductCategoryDto(final ProductCategory productCategory) {

        if(productCategory.getParent() != null) {
            this.parentId = productCategory.getParent().getId();
            this.parentName = productCategory.getParent().getName();
        }
        this.id = productCategory.getId();
        this.name = productCategory.getName();
        this.sequence = productCategory.getSequence();
        this.showYn = productCategory.getShowYn();
        this.children = productCategory.getChildren().stream().map(ProductCategoryDto::new).collect(Collectors.toList());
    }

    public ProductCategory toEntity(ProductCategoryDto productCategoryDto) {

        if(productCategoryDto.getParentId() != null) {

            return ProductCategory.builder()
                    .id(productCategoryDto.getId())
                    .parent(new ProductCategory(productCategoryDto.getParentId()))
                    .name(productCategoryDto.getName())
                    .showYn(productCategoryDto.getShowYn())
                    .sequence(productCategoryDto.getSequence())
                    .build();
        } else {
            return ProductCategory.builder()
                    .id(productCategoryDto.getId())
                    .name(productCategoryDto.getName())
                    .showYn(productCategoryDto.getShowYn())
                    .sequence(productCategoryDto.getSequence())
                    .build();
        }
    }
}
