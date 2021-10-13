package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.ImageFile;
import com.hellostore.ecommerce.entity.ProductComment;
import com.hellostore.ecommerce.entity.ProductCommentImage;
import com.hellostore.ecommerce.repository.ProductCommentImageRepository;
import com.hellostore.ecommerce.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductCommentImageService {

    private final ProductCommentImageRepository productCommentImageRepository;
    private final FileUtil fileUtil;

    @Transactional
    public void removeProductCommentImage(Long productCommentId) throws IOException {
        productCommentImageRepository.removeProductCommentImages(productCommentId);
        ProductCommentImage productCommentImage = productCommentImageRepository.getProductCommentImage(productCommentId);
        fileUtil.deleteIfExists(
                productCommentImage.getImageFile().getFilePath()
                , productCommentImage.getImageFile().getFileName());
    }

    @Transactional
    public void uploadProductCommentImage(List<MultipartFile> productCommentImages,
                                          ProductComment productComment) {

        for (MultipartFile multipartFile : productCommentImages) {

            ImageFile imageFile = fileUtil.fileUpload(multipartFile);

            ProductCommentImage productCommentImage1 = ProductCommentImage.builder()
                    .imageFile(imageFile)
                    .productComment(productComment)
                    .build();

            log.debug("productCommentImage1: {}", productCommentImage1);
            productCommentImageRepository.createProductCommentImage(productCommentImage1);

        }
    }
}
