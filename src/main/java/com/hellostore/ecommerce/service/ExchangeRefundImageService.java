package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.ExchangeRefund;
import com.hellostore.ecommerce.entity.ExchangeRefundImage;
import com.hellostore.ecommerce.repository.ExchangeRefundImageRepository;
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
public class ExchangeRefundImageService {

    private final ExchangeRefundImageRepository exchangeRefundImageRepository;

    @Value("${file.store.path}")
    private String fileStorePath;

    @Transactional
    public void uploadExchangeRefundImage(List<MultipartFile> exchangeRefundImages,
                                          ExchangeRefund exchangeRefund) {


        for (MultipartFile exchangeRefundImage : exchangeRefundImages) {

            String originalFilename = exchangeRefundImage.getOriginalFilename();
            log.debug("OriginalFilename: {}", originalFilename);

            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            long fileSize = exchangeRefundImage.getSize();

            if (!Files.exists(Paths.get(fileStorePath))) {

                try {
                    Files.createDirectories(Paths.get(fileStorePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (InputStream inputStream = exchangeRefundImage.getInputStream()) {

                Files.copy(inputStream, Paths.get(fileStorePath, fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ExchangeRefundImage exchangeRefundImage1 = ExchangeRefundImage.builder().originalFileName(originalFilename)
                    .fileName(fileName).filePath(fileStorePath)
                    .fileSize(fileSize)
                    .exchangeRefund(exchangeRefund)
                    .build();

            log.debug("exchangeRefundImage1: {}", exchangeRefundImage1);
            exchangeRefundImageRepository.createExchangeRefundImage(exchangeRefundImage1);

        }
    }
}
