package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"optionName", "optionValue", "useYn"})
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

    public void setProduct(Product product) {
        this.product = product;
        product.getProductOptions().add(this);
    }

    @Builder
    public ProductOption(Product product, String optionName, String optionValue, String useYn) {
        this.product = product;
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.useYn = useYn;
    }
}
