package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.ExchangeRefund;
import com.hellostore.ecommerce.entity.ExchangeRefundProduct;
import com.hellostore.ecommerce.entity.OrderProduct;
import com.hellostore.ecommerce.enumType.ExchangeRefundStatus;
import com.hellostore.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ExchangeRefundService {

    private final ExchangeRefundRepository exchangeRefundRepository;
    private final ExchangeRefundProductRepository exchangeRefundProductRepository;
    private final OrderProductRepository orderProductRepository;
    private final ExchangeRefundImageService exchangeRefundImageService;
    private final ExchangeRefundImageRepository exchangeRefundImageRepository;
    private final OderProductOptionRepository oderProductOptionRepository;

    @Transactional
    public void createExchangeRefund(ExchangeRefundDto exchangeRefundDto,
                                     List<MultipartFile> exchangeRefundImages) {

        // 교환 환불 신청서 저장
        ExchangeRefund exchangeRefund = ExchangeRefund.builder()
                .exchangeRefundReasonType(exchangeRefundDto.getExchangeRefundReasonType())
                .exchangeRefundStatus(ExchangeRefundStatus.BEFORE)
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

    public Page<ExchangeRefundDto> getExchangeRefunds (
            ExchangeRefundSearchCondition exchangeRefundSearchCondition, Pageable pageable) {

        Page<ExchangeRefundDto> exchangeRefunds
                = exchangeRefundRepository.getExchangeRefunds(exchangeRefundSearchCondition, pageable);

        // exchangeRefund product 가져오기
        List<ExchangeRefundProductDto> exchangeRefundProduct = exchangeRefundProductRepository
                .getExchangeRefundProduct(toExchangeRefundIds(exchangeRefunds.getContent()));

        Map<Long, List<ExchangeRefundProductDto>> collect = exchangeRefundProduct.stream()
                .collect(Collectors.groupingBy(ExchangeRefundProductDto::getExchangeRefundId));

        exchangeRefunds.forEach(e -> e.setExchangeRefundProducts(collect.get(e.getExchangeRefundId())));
        return exchangeRefunds;
    }

    private List<Long> toExchangeRefundIds(List<ExchangeRefundDto> result) {
        return result.stream()
                .map(o -> o.getExchangeRefundId())
                .collect(Collectors.toList());
    }

    private List<Long> toOrderProductIds(List<ExchangeRefundProductDto> result) {
        return result.stream()
                .map(o -> o.getOrderProductId())
                .collect(Collectors.toList());
    }

    public ExchangeRefundDto getExchangeRefund(Long exchangeRefundId) {

        ExchangeRefundDto exchangeRefund
                = exchangeRefundRepository.getExchangeRefund(exchangeRefundId);

        // exchangeRefund image 가져오기
        List<ExchangeRefundImageDto> exchangeRefundImages = exchangeRefundImageRepository
                .getExchangeRefundImages(exchangeRefundId);

        exchangeRefund.setExchangeRefundImages(exchangeRefundImages);

        // exchangeRefund product 가져오기
        List<ExchangeRefundProductDto> exchangeRefundProducts = exchangeRefundProductRepository
                .getExchangeRefundProduct(Arrays.asList(exchangeRefundId));

        // orderProductOptions 조회
        Map<Long, List<OrderProductOptionDto>> orderProductOptionMap
                = oderProductOptionRepository
                .getOrderProductOption(toOrderProductIds(exchangeRefundProducts));

        log.debug("orderProductOptionMap: {}", orderProductOptionMap);
        // orderProduct 루프 돌면서 orderProductOption 추가
        exchangeRefundProducts.forEach(o -> o.setProductOptions(orderProductOptionMap.get(o.getOrderProductId())));

        exchangeRefund.setExchangeRefundProducts(exchangeRefundProducts);

        return exchangeRefund;
    }

    @Transactional
    public void modifyExchangeRefundStatus(List<Long> exchangeRefundIds, ExchangeRefundStatus exchangeRefundStatus) {
        exchangeRefundRepository.modifyExchangeRefundStatus(exchangeRefundIds, exchangeRefundStatus);
    }
}
