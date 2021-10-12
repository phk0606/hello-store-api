package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.ProductComment;
import com.hellostore.ecommerce.entity.ProductCommentImage;
import com.hellostore.ecommerce.repository.ProductCommentImageRepository;
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
public class ProductCommentImageService {

    private final ProductCommentImageRepository productCommentImageRepository;

    @Value("${file.store.path}")
    private String fileStorePath;

    @Transactional
    public void removeProductCommentImage(Long productCommentId) throws IOException {
        productCommentImageRepository.removeProductCommentImages(productCommentId);
        ProductCommentImage productCommentImage = productCommentImageRepository.getProductCommentImage(productCommentId);
        Files.deleteIfExists(Paths.get(productCommentImage.getFilePath(), productCommentImage.getFileName()));
    }

    @Transactional
    public void uploadProductCommentImage(List<MultipartFile> productCommentImages,
                                          ProductComment productComment) {


        for (MultipartFile productCommentImage : productCommentImages) {

            String originalFilename = productCommentImage.getOriginalFilename();
            log.debug("OriginalFilename: {}", originalFilename);

            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            long fileSize = productCommentImage.getSize();

            if (!Files.exists(Paths.get(fileStorePath))) {

                try {
                    Files.createDirectories(Paths.get(fileStorePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (InputStream inputStream = productCommentImage.getInputStream()) {

                Files.copy(inputStream, Paths.get(fileStorePath, fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ProductCommentImage productCommentImage1 = ProductCommentImage.builder().originalFileName(originalFilename)
                    .fileName(fileName).filePath(fileStorePath)
                    .fileSize(fileSize)
                    .productComment(productComment)
                    .build();

            log.debug("productCommentImage1: {}", productCommentImage1);
            productCommentImageRepository.createProductCommentImage(productCommentImage1);

        }
    }
}
