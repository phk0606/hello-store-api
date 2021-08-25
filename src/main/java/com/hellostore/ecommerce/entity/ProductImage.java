package com.hellostore.ecommerce.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    private String imageType; // 리스트: l, 대표: d, 메인: m
}
