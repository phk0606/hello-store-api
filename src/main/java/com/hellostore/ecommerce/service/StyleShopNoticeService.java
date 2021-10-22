package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.CategoryDto;
import com.hellostore.ecommerce.dto.StyleShopNoticeDto;
import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.entity.StyleShopNotice;
import com.hellostore.ecommerce.repository.CategoryDslRepository;
import com.hellostore.ecommerce.repository.StyleShopNoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StyleShopNoticeService {

    private final StyleShopNoticeRepository styleShopNoticeRepository;
    private final CategoryDslRepository categoryDslRepository;

    public StyleShopNoticeDto getCategoryNotice(Long categoryId) {
        return styleShopNoticeRepository.getCategoryNotice(categoryId);
    }

    @Transactional
    public void mergeNoticeContent(StyleShopNoticeDto styleShopNoticeDto) {

        if (styleShopNoticeDto.getAllCategoriesSame()) {
            List<CategoryDto> childCategories = categoryDslRepository.getChildCategories();
            for (CategoryDto childCategory : childCategories) {
                if (ObjectUtils.isEmpty(this.getCategoryNotice(childCategory.getId()))) {
                    Category category
                            = categoryDslRepository.getCategoryOne(childCategory.getId());
                    StyleShopNotice styleShopNotice1
                            = StyleShopNotice.builder()
                            .category(category)
                            .content(styleShopNoticeDto.getContent()).build();
                    styleShopNoticeRepository.createStyleShopNotice(styleShopNotice1);
                } else {
                    styleShopNoticeRepository
                            .updateStyleShopNotice(
                                    childCategory.getId(), styleShopNoticeDto.getContent());
                }
            }
        } else {
            if (!ObjectUtils.isEmpty(styleShopNoticeDto.getId())) {
                styleShopNoticeRepository.updateStyleShopNotice(styleShopNoticeDto);
            } else {
                Category category
                        = categoryDslRepository.getCategoryOne(styleShopNoticeDto.getCategoryId());
                StyleShopNotice styleShopNotice1
                        = StyleShopNotice.builder()
                        .category(category)
                        .content(styleShopNoticeDto.getContent()).build();
                styleShopNoticeRepository.createStyleShopNotice(styleShopNotice1);
            }
        }
    }
}
