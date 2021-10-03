package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.FaqDto;
import com.hellostore.ecommerce.dto.FaqSearchCondition;
import com.hellostore.ecommerce.entity.Faq;
import com.hellostore.ecommerce.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FaqService {

    private final FaqRepository faqRepository;

    @Transactional
    public void createFaq(FaqDto faqDto) {

        Faq faq = Faq.builder().question(faqDto.getQuestion())
                .answer(faqDto.getAnswer())
                .faqType(faqDto.getFaqType())
                .build();

        faqRepository.save(faq);
    }

    public Page<FaqDto> getFaqs(FaqSearchCondition faqSearchCondition, Pageable pageable) {
        return faqRepository.getFaqs(faqSearchCondition, pageable);
    }

    @Transactional
    public void modifyFaq(FaqDto faqDto) {
        faqRepository.modifyFaq(faqDto);
    }

    @Transactional
    public void removeFaq(FaqDto faqDto) {
        faqRepository.removeFaq(faqDto);
    }

    public FaqDto getFaq(Long faqId) {
        return faqRepository.getFaq(faqId);
    }
}
