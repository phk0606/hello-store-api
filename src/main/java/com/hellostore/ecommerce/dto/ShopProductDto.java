package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ShopProductDto {

    private Long categoryId;
    private String categoryName;
    private Long productId;
    private String productName;
    private Integer salePrice;
    private Integer regularPrice;
    private String description;
    private Boolean newArrival;
    private Boolean best;
    private Boolean discount;
    private Long imageId;
    private String originalFileName;
    private String fileName;
    private String filePath;
    private long fileSize;
    private ImageType imageType;

    @QueryProjection
    public ShopProductDto(Long categoryId, String categoryName, Long productId, String productName, Integer salePrice, Integer regularPrice, String description, Boolean newArrival, Boolean best, Boolean discount, Long imageId, String originalFileName, String fileName, String filePath, long fileSize, ImageType imageType) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productId = productId;
        this.productName = productName;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.description = description;
        this.newArrival = newArrival;
        this.best = best;
        this.discount = discount;
        this.imageId = imageId;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.imageType = imageType;
    }
}
