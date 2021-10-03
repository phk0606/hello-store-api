package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.FaqDto;
import com.hellostore.ecommerce.dto.FaqSearchCondition;
import com.hellostore.ecommerce.dto.FaqTypeDto;
import com.hellostore.ecommerce.enumType.FaqType;
import com.hellostore.ecommerce.service.FaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    private final FaqService faqService;

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

    @GetMapping("/getFaqs")
    public Page<FaqDto> getFaqs(FaqSearchCondition faqSearchCondition,
                                Pageable pageable) {
        log.debug("faqSearchCondition: {}", faqSearchCondition);
        return faqService.getFaqs(faqSearchCondition, pageable);
    }

    @GetMapping("/getFaq")
    public FaqDto getFaq(@RequestParam Long faqId) {
        log.debug("faqId: {}", faqId);
        return faqService.getFaq(faqId);
    }

    @PutMapping("/modifyFaq")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyFaq(@RequestBody FaqDto faqDto) {
        faqService.modifyFaq(faqDto);
    }

    @DeleteMapping("/removeFaq")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeFaq(@RequestBody FaqDto faqDto) {
        faqService.removeFaq(faqDto);
    }

    @PostMapping("/createFaq")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createFaq(@RequestBody FaqDto faqDto) {
        log.debug("faqDto: {}", faqDto);
        faqService.createFaq(faqDto);
    }
}
