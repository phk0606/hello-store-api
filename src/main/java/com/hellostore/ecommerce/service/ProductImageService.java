package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductImageDto;
import com.hellostore.ecommerce.entity.ImageFile;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.enumType.ImageType;
import com.hellostore.ecommerce.repository.ProductImageRepository;
import com.hellostore.ecommerce.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final FileUtil fileUtil;

    public ProductImageDto getListImage(Long productId) throws IOException {
        ProductImage listImage = productImageRepository.getListImage(productId);

        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setByteImage(
                Files.readAllBytes(
                        Paths.get(listImage.getImageFile().getFilePath(), listImage.getImageFile().getFileName())));
        return productImageDto;
    }

    @Transactional
    public void removeProductImage(Long productId) throws IOException {
        List<ProductImage> productImages = productImageRepository.getProductImages(productId);
        for (ProductImage productImage : productImages) {
            fileUtil.deleteIfExists(productImage.getImageFile().getFilePath(), productImage.getImageFile().getFileName());
        }
        productImageRepository.removeProductImage(productId);
    }

    @Transactional
    public void uploadProductImage(List<MultipartFile> productImages, Product product) throws IOException {

        for (MultipartFile multipartFile : productImages) {

            log.debug("OriginalFilename: {}", multipartFile.getOriginalFilename());
            String fileNameWithImageType[] = multipartFile.getOriginalFilename().split("_");
            ImageType imageType = ImageType.valueOf(fileNameWithImageType[0]);

            ImageFile imageFile = fileUtil.fileUpload(multipartFile);

            ProductImage productImage1 = ProductImage.builder()
                    .imageFile(imageFile)
                    .imageType(imageType)
                    .product(product)
                    .build();

            log.debug("productImage1: {}", productImage1);
            productImageRepository.createProductImage(productImage1);

        }
    }
}
