package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.CategoryDto;
import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.entity.StyleShopNotice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class StyleShopNoticeRepositoryTest {

    @Autowired
    private StyleShopNoticeRepository styleShopNoticeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDslRepository categoryDslRepository;

    @Test
    public void createStyleShopNotice() {
        Optional<Category> category = categoryRepository.findById(5l);
        String content = "[신상품 입고 안내]\n" +
                "기모 티셔츠 상품이 새로 입고 되었습니다. " +
                "새로 입고된 상품은 season off 상품이지만 가격대비  품질이 우수하여, " +
                "고객님들께 선보이게 되었습니다. \n";
        StyleShopNotice styleShopNotice
                = StyleShopNotice.builder()
                .category(category.get()).content(content).build();
        styleShopNoticeRepository.createStyleShopNotice(styleShopNotice);
    }

    @Test
    public void getChildCategories() {
        List<CategoryDto> childCategories = categoryDslRepository.getChildCategories();
        log.debug("childCategories, {}", childCategories);
    }
}