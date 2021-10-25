package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = true)
@Slf4j
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository productCategoryRepository;

    @Test
    public void testProductCategory() {

        Category productCategory1 = new Category(null,null, "OUTER", 1, "Y");
        Category productCategory2 = new Category(null,null, "TOP", 2, "Y");
        Category productCategory3 = new Category(null,null, "BOTTOM", 3, "Y");
        Category productCategory4 = new Category(null,null, "DRESS", 4, "Y");

        Category productCategory11 = new Category(null,productCategory1, "CARDIGAN", 1, "Y");
        Category productCategory12 = new Category(null,productCategory1, "JACKET/JUMPER", 2, "Y");
        Category productCategory13 = new Category(null,productCategory1, "COAT", 3, "Y");
        Category productCategory14 = new Category(null,productCategory1, "VEST", 4, "Y");
        Category productCategory15 = new Category(null,productCategory1, "PADDING", 5, "Y");

        Category productCategory21 = new Category(null,productCategory2, "BLOUSE/SHIRT", 1, "Y");
        Category productCategory22 = new Category(null,productCategory2, "TEE", 2, "Y");
        Category productCategory23 = new Category(null,productCategory2, "MTM/HOOD", 3, "Y");
        Category productCategory24 = new Category(null,productCategory2, "KNIT/SWEATER", 4, "Y");
        Category productCategory25 = new Category(null,productCategory2, "SLEEVELESS", 5, "Y");

        Category productCategory31 = new Category(null,productCategory3, "PANTS", 1, "Y");
        Category productCategory32 = new Category(null,productCategory3, "SKIRT", 2, "Y");

        Category productCategory41 = new Category(null,productCategory4, "KNIT", 1, "Y");
        Category productCategory42 = new Category(null,productCategory4, "LONG", 2, "Y");
        Category productCategory43 = new Category(null,productCategory4, "FLOWER", 3, "Y");
        Category productCategory44 = new Category(null,productCategory4, "STRIPE", 4, "Y");
        Category productCategory45 = new Category(null,productCategory4, "OFF SHOULDER", 5, "Y");
        Category productCategory46 = new Category(null,productCategory4, "LINEN", 6, "Y");
        Category productCategory47 = new Category(null,productCategory4, "MINI/MIDI", 7, "Y");
        Category productCategory48 = new Category(null,productCategory4, "HOOD", 8, "Y");
        Category productCategory49 = new Category(null,productCategory4, "SEE-THROUGH", 9, "Y");
        Category productCategory50 = new Category(null,productCategory4, "CHIFFON", 10, "Y");
        Category productCategory51 = new Category(null,productCategory4, "SLEEVELESS", 11, "Y");
        Category productCategory52 = new Category(null,productCategory4, "PATTERN", 12, "Y");
        Category productCategory53 = new Category(null,productCategory4, "SUSPENDERS", 13, "Y");

        productCategoryRepository.save(productCategory1);
        productCategoryRepository.save(productCategory2);
        productCategoryRepository.save(productCategory3);
        productCategoryRepository.save(productCategory4);

        productCategoryRepository.save(productCategory11);
        productCategoryRepository.save(productCategory12);
        productCategoryRepository.save(productCategory13);
        productCategoryRepository.save(productCategory14);
        productCategoryRepository.save(productCategory15);

        productCategoryRepository.save(productCategory21);
        productCategoryRepository.save(productCategory22);
        productCategoryRepository.save(productCategory23);
        productCategoryRepository.save(productCategory24);
        productCategoryRepository.save(productCategory25);

        productCategoryRepository.save(productCategory31);
        productCategoryRepository.save(productCategory32);

        productCategoryRepository.save(productCategory41);
        productCategoryRepository.save(productCategory42);
        productCategoryRepository.save(productCategory43);
        productCategoryRepository.save(productCategory44);
        productCategoryRepository.save(productCategory45);
        productCategoryRepository.save(productCategory46);
        productCategoryRepository.save(productCategory47);
        productCategoryRepository.save(productCategory48);
        productCategoryRepository.save(productCategory49);
        productCategoryRepository.save(productCategory50);
        productCategoryRepository.save(productCategory51);
        productCategoryRepository.save(productCategory52);
        productCategoryRepository.save(productCategory53);

        List<Category> all = productCategoryRepository.findAll();
        log.debug("productCategoryList: {}", all);

    }
}