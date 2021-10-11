package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(uniqueConstraints
        = {@UniqueConstraint(columnNames = {"product_id", "first_option_id", "second_option_id"})})
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_quantity_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "first_option_id")
    private ProductOption firstOption;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "second_option_id")
    private ProductOption secondOption;

    private int stockQuantity;

    @Builder
    public StockQuantity(Product product, ProductOption firstOption, ProductOption secondOption, int stockQuantity) {
        this.product = product;
        this.firstOption = firstOption;
        this.secondOption = secondOption;
        this.stockQuantity = stockQuantity;
    }
}
