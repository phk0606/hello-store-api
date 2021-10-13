package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ImageFile {

    private String originalFileName;
    private String fileName;
    private String filePath;

    private long fileSize;
}
