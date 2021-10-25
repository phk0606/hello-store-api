package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCommentImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Embedded
    private ImageFile imageFile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_comment_id")
    private ProductComment productComment;

    @Builder
    public ProductCommentImage(ImageFile imageFile, ProductComment productComment) {
        this.imageFile = imageFile;
        this.productComment = productComment;
    }
}
