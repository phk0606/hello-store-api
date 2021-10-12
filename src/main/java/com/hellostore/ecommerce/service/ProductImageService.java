package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductImageDto;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.enumType.ImageType;
import com.hellostore.ecommerce.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Value("${file.store.path}")
    private String fileStorePath;

    public ProductImageDto getListImage(Long productId) throws IOException {
        ProductImage listImage = productImageRepository.getListImage(productId);

        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setByteImage(
                Files.readAllBytes(
                        Paths.get(listImage.getFilePath(), listImage.getFileName())));
        return productImageDto;
    }

    @Transactional
    public void removeProductImage(Long productId) throws IOException {
        List<ProductImage> productImages = productImageRepository.getProductImages(productId);
        for (ProductImage productImage : productImages) {
            Files.deleteIfExists(Paths.get(productImage.getFilePath(), productImage.getFileName()));
        }
        productImageRepository.removeProductImage(productId);
    }

    @Transactional
    public void uploadProductImage(List<MultipartFile> productImages, Product product) {


        for (MultipartFile productImage : productImages) {

            log.debug("OriginalFilename: {}", productImage.getOriginalFilename());
            String fileNameWithImageType[] = productImage.getOriginalFilename().split("_");
            ImageType imageType = ImageType.valueOf(fileNameWithImageType[0]);
            String originalFileName = fileNameWithImageType[1];

            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
            long fileSize = productImage.getSize();

            if (!Files.exists(Paths.get(fileStorePath))) {

                try {
                    Files.createDirectories(Paths.get(fileStorePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (InputStream inputStream = productImage.getInputStream()) {

                Files.copy(inputStream, Paths.get(fileStorePath, fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ProductImage productImage1 = ProductImage.builder().originalFileName(originalFileName)
                    .fileName(fileName).filePath(fileStorePath)
                    .fileSize(fileSize)
                    .imageType(imageType)
                    .product(product)
                    .build();

            log.debug("productImage1: {}", productImage1);
            productImageRepository.createProductImage(productImage1);

        }
    }
}
