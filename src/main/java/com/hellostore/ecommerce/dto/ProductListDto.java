package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellostore.ecommerce.enumType.ImageType;
import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class ProductListDto {

    private Long categoryId;
    private String categoryName;
    private Long productId;
    private String name;
    private Integer salePrice;

    private ProductShowType productShowType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createBy;

    @Setter
    private byte[] image;
    private Long imageId;
    private String originalFileName;
    private String fileName;
    private String filePath;
    private long fileSize;

    private ImageType imageType;
    private Integer clickCount;


    @QueryProjection
    public ProductListDto(Long categoryId, String categoryName, Long productId, String name, Integer salePrice, ProductShowType productShowType, Integer clickCount, LocalDateTime createdDate, LocalDateTime lastModifiedDate, String createBy, Long imageId, String originalFileName, String fileName, String filePath, long fileSize, ImageType imageType) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productId = productId;
        this.name = name;
        this.salePrice = salePrice;
        this.productShowType = productShowType;
        this.clickCount = clickCount;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.createBy = createBy;
        this.imageId = imageId;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.imageType = imageType;
    }
}
