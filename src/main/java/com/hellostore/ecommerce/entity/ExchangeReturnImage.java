package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeReturnImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_return_image_id")
    private Long id;

    @Embedded
    private ImageFile imageFile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "exchange_return_id")
    private ExchangeReturn exchangeReturn;

    @Builder
    public ExchangeReturnImage(ImageFile imageFile, ExchangeReturn exchangeReturn) {
        this.imageFile = imageFile;
        this.exchangeReturn = exchangeReturn;
    }
}
