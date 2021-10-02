package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.FaqTypeDto;
import com.hellostore.ecommerce.enumType.FaqType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    public void removeProducts() {

        List<Long> productIds = new ArrayList<>();
        productIds.add(10l);
        productIds.add(11l);
        productIds.add(12l);

        productService.removeProducts(productIds);

    }

    @Test
    public void test() {

        List<FaqTypeDto> faqTypes = new ArrayList<>();
        List<FaqType> collect1 = Arrays.asList(FaqType.values()).stream().sorted(Comparator.comparing(FaqType::getSequence)).collect(Collectors.toList());
        for (FaqType faqType : collect1) {
            log.debug("faqType: {}", faqType);
            faqTypes.add(new FaqTypeDto(faqType, faqType.getValue()));
        }
        log.debug("faqTypes: {}", faqTypes);
    }
}