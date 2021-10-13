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

    @Embedded
    private ImageFile imageFile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "exchange_refund_id")
    private ExchangeRefund exchangeRefund;

    @Builder
    public ExchangeRefundImage(ImageFile imageFile, ExchangeRefund exchangeRefund) {
        this.imageFile = imageFile;
        this.exchangeRefund = exchangeRefund;
    }
}
