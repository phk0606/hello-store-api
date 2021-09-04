package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@ToString
public class CategoryDto {

    private Long id;
    private String name;
    @Setter
    private Integer sequence;
    private String showYn;
    private Long parentId;
    private String parentName;

    private List<CategoryDto> children;

    public CategoryDto(final Category category) {

        if(category.getParent() != null) {
            this.parentId = category.getParent().getId();
            this.parentName = category.getParent().getName();
        }
        this.id = category.getId();
        this.name = category.getName();
        this.sequence = category.getSequence();
        this.showYn = category.getShowYn();
        this.children = category.getChildren().stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    public Category toEntity(CategoryDto categoryDto) {

        if(categoryDto.getParentId() != null) {

            return Category.builder()
                    .id(categoryDto.getId())
                    .parent(new Category(categoryDto.getParentId()))
                    .name(categoryDto.getName())
                    .showYn(categoryDto.getShowYn())
                    .sequence(categoryDto.getSequence())
                    .build();
        } else {
            return Category.builder()
                    .id(categoryDto.getId())
                    .name(categoryDto.getName())
                    .showYn(categoryDto.getShowYn())
                    .sequence(categoryDto.getSequence())
                    .build();
        }
    }
}
