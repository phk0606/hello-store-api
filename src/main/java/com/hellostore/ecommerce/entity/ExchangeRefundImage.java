package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeRefundImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_refund_image_id")
    private Long id;

    private String originalFileName;
    private String fileName;
    private String filePath;

    private long fileSize;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "exchange_refund_id")
    private ExchangeRefund exchangeRefund;

    @Builder
    public ExchangeRefundImage(String originalFileName, String fileName, String filePath, long fileSize, ExchangeRefund exchangeRefund) {
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.exchangeRefund = exchangeRefund;
    }
}
