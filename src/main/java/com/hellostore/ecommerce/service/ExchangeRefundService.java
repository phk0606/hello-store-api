package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ExchangeRefundDto;
import com.hellostore.ecommerce.dto.ExchangeRefundProductDto;
import com.hellostore.ecommerce.entity.ExchangeRefund;
import com.hellostore.ecommerce.entity.ExchangeRefundProduct;
import com.hellostore.ecommerce.entity.OrderProduct;
import com.hellostore.ecommerce.repository.ExchangeRefundProductRepository;
import com.hellostore.ecommerce.repository.ExchangeRefundRepository;
import com.hellostore.ecommerce.repository.OrderProductRepository;
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
public class ExchangeRefundService {

    private final ExchangeRefundRepository exchangeRefundRepository;
    private final ExchangeRefundProductRepository exchangeRefundProductRepository;
    private final OrderProductRepository orderProductRepository;
    private final ExchangeRefundImageService exchangeRefundImageService;

    @Transactional
    public void createExchangeRefund(ExchangeRefundDto exchangeRefundDto,
                                     List<MultipartFile> exchangeRefundImages) {

        // 교환 환불 신청서 저장
        ExchangeRefund exchangeRefund = ExchangeRefund.builder()
                .exchangeRefundReasonType(exchangeRefundDto.getExchangeRefundReasonType())
                .content(exchangeRefundDto.getContent()).build();

        ExchangeRefund exchangeRefund1 = exchangeRefundRepository.createExchangeRefund(exchangeRefund);

        // 교환 환불 상품 저장
        List<ExchangeRefundProductDto> exchangeRefundProducts = exchangeRefundDto.getExchangeRefundProducts();
        for (ExchangeRefundProductDto exchangeRefundProduct : exchangeRefundProducts) {

            OrderProduct orderProduct
                    = orderProductRepository
                    .getOrderProductById(exchangeRefundProduct.getOrderProductId());

            ExchangeRefundProduct exchangeRefundProduct1 = ExchangeRefundProduct.builder()
                    .exchangeRefund(exchangeRefund1)
                    .exchangeRefundType(exchangeRefundProduct.getExchangeRefundType())
                    .orderProduct(orderProduct)
                    .build();

            exchangeRefundProductRepository.createExchangeRefundProduct(exchangeRefundProduct1);
        }

        // 교환 환불 이미지 저장
        if(exchangeRefundImages != null) {
            exchangeRefundImageService.uploadExchangeRefundImage(exchangeRefundImages, exchangeRefund1);
        }
    }
}
