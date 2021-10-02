package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.FaqTypeDto;
import com.hellostore.ecommerce.enumType.FaqType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
@Slf4j
public class FaqController {

    @GetMapping("/getCategories")
    public List<FaqTypeDto> getCategories() {

        List<FaqTypeDto> faqTypes = new ArrayList<>();
        List<FaqType> collect1 = Arrays.asList(FaqType.values())
                .stream().sorted(Comparator.comparing(FaqType::getSequence)).collect(Collectors.toList());
        for (FaqType faqType : collect1) {
            log.debug("faqType: {}", faqType);
            faqTypes.add(new FaqTypeDto(faqType, faqType.getValue()));
        }

        return faqTypes;
    }
}
