package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"optionGroupNumber", "optionName", "optionValue"})
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productoption_id")
    private Long id;

    private Integer optionGroupNumber;
    private String optionName;
    private String optionValue;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
//        product.getProductOptions().add(this);
    }


    public ProductOption(Product product, String optionName, String optionValue) {
        this.product = product;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    @Builder
    public ProductOption(Integer optionGroupNumber, Product product, String optionName, String optionValue) {
        this.optionGroupNumber = optionGroupNumber;
        this.product = product;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }
}
