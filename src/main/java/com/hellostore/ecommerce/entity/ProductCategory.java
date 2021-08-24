package com.hellostore.ecommerce.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "parentId", "categoryName", "sequence", "showYn"})
public class ProductCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Integer id;

    private Integer parentId;
    private String categoryName;

    private Integer sequence;
    private String showYn;

    public ProductCategory(Integer parentId, String categoryName, Integer sequence, String showYn) {
        this.parentId = parentId;
        this.categoryName = categoryName;
        this.sequence = sequence;
        this.showYn = showYn;
    }
}
