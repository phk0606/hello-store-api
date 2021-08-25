package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.ImageType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Integer id;

    private String originalFileName;
    private String filePath;

    private long fileSize;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;
}
