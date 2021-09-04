package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.ImageType;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"originalFileName", "fileName", "filePath", "fileSize", "imageType"})
public class ProductImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String originalFileName;
    private String fileName;
    private String filePath;

    private long fileSize;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ProductImage(String originalFileName, String fileName, String filePath, long fileSize, ImageType imageType, Product product) {
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.imageType = imageType;
        this.product = product;
    }
}
