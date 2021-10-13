package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ImageFile;
import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.enumType.ImageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ProductImageDto {

    private Long id;

    private ImageFile imageFile;

    private ImageType imageType;

    @Setter
    private byte[] byteImage;

    public ProductImageDto(ProductImage productImage) {
        this.id = productImage.getId();
        this.imageFile = productImage.getImageFile();
        this.imageType = productImage.getImageType();
    }
}
