package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor
@ToString
@Slf4j
public class CategorySelectDto {

    private String text;
    private Long value;

    public CategorySelectDto(Category category) {
        this.text = category.getName();
        this.value = category.getId();
    }
}
