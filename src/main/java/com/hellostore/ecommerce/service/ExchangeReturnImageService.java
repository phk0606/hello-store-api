package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.ExchangeReturn;
import com.hellostore.ecommerce.entity.ExchangeReturnImage;
import com.hellostore.ecommerce.entity.ImageFile;
import com.hellostore.ecommerce.repository.ExchangeReturnImageRepository;
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
public class ExchangeReturnImageService {

    private final ExchangeReturnImageRepository exchangeReturnImageRepository;
    private final FileUtil fileUtil;

    @Transactional
    public void uploadExchangeReturnImage(List<MultipartFile> exchangeReturnImages,
                                          ExchangeReturn exchangeReturn) {

        for (MultipartFile multipartFile : exchangeReturnImages) {

            ImageFile imageFile = fileUtil.fileUpload(multipartFile);

            ExchangeReturnImage exchangeReturnImage1 = ExchangeReturnImage.builder()
                    .imageFile(imageFile)
                    .exchangeReturn(exchangeReturn)
                    .build();

            log.debug("exchangeReturnImage1: {}", exchangeReturnImage1);
            exchangeReturnImageRepository.createExchangeReturnImage(exchangeReturnImage1);
        }
    }
}
