package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "sequence", "showYn"})
public class ProductCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private ProductCategory parent;
    private String name;

    private Integer sequence;
    private String showYn;

    @OrderBy("sequence asc ")
    @OneToMany(mappedBy = "parent")
    private List<ProductCategory> children = new ArrayList<>();

    @Builder
    public ProductCategory(ProductCategory parent, String name, Integer sequence, String showYn) {
        this.parent = parent;
        this.name = name;
        this.sequence = sequence;
        this.showYn = showYn;
    }
}
