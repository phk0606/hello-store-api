package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.enumType.ImageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ProductImageDto {

    private Long id;

    private String originalFileName;
    private String fileName;
    private String filePath;

    private long fileSize;

    private ImageType imageType;

    @Setter
    private byte[] byteImage;

    public ProductImageDto(ProductImage productImage) {
        this.id = productImage.getId();
        this.originalFileName = productImage.getOriginalFileName();
        this.fileName = productImage.getFileName();
        this.filePath = productImage.getFilePath();
        this.fileSize = productImage.getFileSize();
        this.imageType = productImage.getImageType();
    }
}
