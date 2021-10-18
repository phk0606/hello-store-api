package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.ExchangeReturn;
import com.hellostore.ecommerce.entity.ExchangeReturnProduct;
import com.hellostore.ecommerce.entity.Order;
import com.hellostore.ecommerce.entity.OrderProduct;
import com.hellostore.ecommerce.enumType.ExchangeReturnStatus;
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
public class ExchangeReturnService {

    private final ExchangeReturnRepository exchangeReturnRepository;
    private final ExchangeReturnProductRepository exchangeReturnProductRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ExchangeReturnImageService exchangeReturnImageService;
    private final ExchangeReturnImageRepository exchangeReturnImageRepository;
    private final OderProductOptionRepository oderProductOptionRepository;

    @Transactional
    public void createExchangeReturn(ExchangeReturnDto exchangeReturnDto,
                                     List<MultipartFile> exchangeReturnImages) {

        Order order = orderRepository.findOne(exchangeReturnDto.getOrderId());
        // 교환 환불 신청서 저장
        ExchangeReturn exchangeReturn = ExchangeReturn.builder()
                .order(order)
                .exchangeReturnReasonType(exchangeReturnDto.getExchangeReturnReasonType())
                .exchangeReturnStatus(ExchangeReturnStatus.REQUESTED)
                .content(exchangeReturnDto.getContent()).build();

        ExchangeReturn exchangeReturn1 = exchangeReturnRepository.createExchangeReturn(exchangeReturn);

        // 교환 환불 상품 저장
        List<ExchangeReturnProductDto> exchangeReturnProducts = exchangeReturnDto.getExchangeReturnProducts();
        for (ExchangeReturnProductDto exchangeReturnProduct : exchangeReturnProducts) {

            OrderProduct orderProduct
                    = orderProductRepository
                    .getOrderProductById(exchangeReturnProduct.getOrderProductId());

            ExchangeReturnProduct exchangeReturnProduct1 = ExchangeReturnProduct.builder()
                    .exchangeReturn(exchangeReturn1)
                    .exchangeReturnType(exchangeReturnProduct.getExchangeReturnType())
                    .orderProduct(orderProduct)
                    .build();

            exchangeReturnProductRepository.createExchangeReturnProduct(exchangeReturnProduct1);
        }

        // 교환 환불 이미지 저장
        if(exchangeReturnImages != null) {
            exchangeReturnImageService.uploadExchangeReturnImage(exchangeReturnImages, exchangeReturn1);
        }
    }

    public Page<ExchangeReturnDto> getExchangeReturns (
            ExchangeReturnSearchCondition exchangeReturnSearchCondition, Pageable pageable) {

        Page<ExchangeReturnDto> exchangeReturns
                = exchangeReturnRepository.getExchangeReturns(exchangeReturnSearchCondition, pageable);

        // exchangeRefund product 가져오기
        List<ExchangeReturnProductDto> exchangeReturnProduct = exchangeReturnProductRepository
                .getExchangeReturnProduct(toExchangeReturnIds(exchangeReturns.getContent()));

        Map<Long, List<ExchangeReturnProductDto>> collect = exchangeReturnProduct.stream()
                .collect(Collectors.groupingBy(ExchangeReturnProductDto::getExchangeReturnId));

        exchangeReturns.forEach(e -> e.setExchangeReturnProducts(collect.get(e.getExchangeReturnId())));
        return exchangeReturns;
    }

    private List<Long> toExchangeReturnIds(List<ExchangeReturnDto> result) {
        return result.stream()
                .map(o -> o.getExchangeReturnId())
                .collect(Collectors.toList());
    }

    private List<Long> toOrderProductIds(List<ExchangeReturnProductDto> result) {
        return result.stream()
                .map(o -> o.getOrderProductId())
                .collect(Collectors.toList());
    }

    public ExchangeReturnDto getExchangeReturn(Long exchangeReturnId) {

        ExchangeReturnDto exchangeReturn
                = exchangeReturnRepository.getExchangeReturn(exchangeReturnId);

        // exchangeRefund image 가져오기
        List<ExchangeReturnImageDto> exchangeReturnImages = exchangeReturnImageRepository
                .getExchangeReturnImages(exchangeReturnId);

        exchangeReturn.setExchangeReturnImages(exchangeReturnImages);

        // exchangeRefund product 가져오기
        List<ExchangeReturnProductDto> exchangeReturnProducts = exchangeReturnProductRepository
                .getExchangeReturnProduct(Arrays.asList(exchangeReturnId));

        // orderProductOptions 조회
        Map<Long, List<OrderProductOptionDto>> orderProductOptionMap
                = oderProductOptionRepository
                .getOrderProductOption(toOrderProductIds(exchangeReturnProducts));

        log.debug("orderProductOptionMap: {}", orderProductOptionMap);
        // orderProduct 루프 돌면서 orderProductOption 추가
        exchangeReturnProducts.forEach(o -> o.setProductOptions(orderProductOptionMap.get(o.getOrderProductId())));

        exchangeReturn.setExchangeReturnProducts(exchangeReturnProducts);

        return exchangeReturn;
    }

    @Transactional
    public void modifyExchangeReturnStatus(List<Long> exchangeReturnIds, ExchangeReturnStatus exchangeReturnStatus) {
        exchangeReturnRepository.modifyExchangeReturnStatus(exchangeReturnIds, exchangeReturnStatus);
    }
}
