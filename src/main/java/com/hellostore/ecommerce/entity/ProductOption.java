package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {

    @Id
    @GeneratedValue
    @Column(name = "productoption_id")
    private Integer id;

    private String optionName;
    private String optionValue;

    private String useYn;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ProductOption(String optionName, String optionValue, String useYn) {
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.useYn = useYn;
    }
}
