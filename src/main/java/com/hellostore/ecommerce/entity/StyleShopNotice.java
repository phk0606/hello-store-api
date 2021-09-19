package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StyleShopNotice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "style_shop_notice_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String content;

    @Builder
    public StyleShopNotice(Category category, String content) {
        this.category = category;
        this.content = content;
    }
}
