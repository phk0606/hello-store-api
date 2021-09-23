package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_comment_id")
    private Long id;

    @OneToMany(mappedBy = "productComment", cascade = CascadeType.ALL)
    private List<ProductCommentImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String content;
    private int grade;

    @OneToMany(mappedBy = "productComment", cascade = CascadeType.ALL)
    private List<ProductCommentReply> replies = new ArrayList<>();

    @Builder
    public ProductComment(Product product, String content, int grade) {
        this.product = product;
        this.content = content;
        this.grade = grade;
    }
}
