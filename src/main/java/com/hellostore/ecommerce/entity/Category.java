package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "sequence", "showYn"})
public class Category extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    private String name;

    private Integer sequence;
    private String showYn;

    @OrderBy("sequence asc ")
    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    public Category(Long id) {
        this.id = id;
    }

    @Builder
    public Category(Long id, Category parent, String name, Integer sequence, String showYn) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.sequence = sequence;
        this.showYn = showYn;
    }
}
