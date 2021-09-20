package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"originalFileName", "fileName", "filePath", "fileSize", "imageType"})
public class ProductCommentImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String originalFileName;
    private String fileName;
    private String filePath;

    private long fileSize;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_comment_id")
    private ProductComment productComment;

    @Builder
    public ProductCommentImage(String originalFileName, String fileName, String filePath, long fileSize, ProductComment productComment) {
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.productComment = productComment;
    }
}
