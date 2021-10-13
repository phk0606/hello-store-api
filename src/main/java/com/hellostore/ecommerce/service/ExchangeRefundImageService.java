package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.ExchangeRefund;
import com.hellostore.ecommerce.entity.ExchangeRefundImage;
import com.hellostore.ecommerce.entity.ImageFile;
import com.hellostore.ecommerce.repository.ExchangeRefundImageRepository;
import com.hellostore.ecommerce.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ExchangeRefundImageService {

    private final ExchangeRefundImageRepository exchangeRefundImageRepository;
    private final FileUtil fileUtil;

    @Transactional
    public void uploadExchangeRefundImage(List<MultipartFile> exchangeRefundImages,
                                          ExchangeRefund exchangeRefund) {

        for (MultipartFile multipartFile : exchangeRefundImages) {

            ImageFile imageFile = fileUtil.fileUpload(multipartFile);

            ExchangeRefundImage exchangeRefundImage1 = ExchangeRefundImage.builder()
                    .imageFile(imageFile)
                    .exchangeRefund(exchangeRefund)
                    .build();

            log.debug("exchangeRefundImage1: {}", exchangeRefundImage1);
            exchangeRefundImageRepository.createExchangeRefundImage(exchangeRefundImage1);
        }
    }
}
