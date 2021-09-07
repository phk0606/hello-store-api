package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.enumType.ImageType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ShopProductDto {

    private Long categoryId;
    private String categoryName;
    private Long productId;
    private String productName;
    private int salePrice;
    private int regularPrice;
    private String description;
    private Boolean newArrival;
    private Boolean best;
    private Boolean discount;
    private String detailInfo;
    private String shippingInfo;
    private String exchangeReturnInfo;
    private ProductShowType productShowType;

    @Setter
    private byte[] image;
    private Long imageId;
    private String originalFileName;
    private String fileName;
    private String filePath;
    private long fileSize;
    private ImageType imageType;

    private List<ProductOption> firstOptions = new ArrayList<>();
    private List<ProductOption> secondOptions = new ArrayList<>();

    @Setter
    private List<byte[]> byteImages = new ArrayList<>();

    @QueryProjection
    public ShopProductDto(Long categoryId, String categoryName, Long productId, String productName, int salePrice, int regularPrice, String description, Boolean newArrival, Boolean best, Boolean discount, ProductShowType productShowType, Long imageId, String originalFileName, String fileName, String filePath, long fileSize, ImageType imageType) {
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
        this.productShowType = productShowType;
        this.imageId = imageId;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.imageType = imageType;
    }

    @QueryProjection
    public ShopProductDto(Long productId, String productName, int salePrice, int regularPrice, String description, Boolean newArrival, Boolean best, Boolean discount, ProductShowType productShowType, String detailInfo, String shippingInfo, String exchangeReturnInfo) {

        this.productId = productId;
        this.productName = productName;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.description = description;
        this.newArrival = newArrival;
        this.best = best;
        this.discount = discount;
        this.productShowType = productShowType;
        this.detailInfo= detailInfo;
        this.shippingInfo = shippingInfo;
        this.exchangeReturnInfo = exchangeReturnInfo;
    }
}
