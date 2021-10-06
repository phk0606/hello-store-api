package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ExchangeRefundImageDto {

    private String originalFileName;
    private String fileName;
    private String filePath;

    private long fileSize;

    @QueryProjection
    public ExchangeRefundImageDto(String originalFileName, String fileName, String filePath, long fileSize) {
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
