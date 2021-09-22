package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_answer_id")
    private Long id;

    private String content;

    @OneToOne
    @JoinColumn(name = "product_question_id")
    private ProductQuestion productQuestion;

    @Builder
    public ProductAnswer(String content, ProductQuestion productQuestion) {
        this.content = content;
        this.productQuestion = productQuestion;
    }
}
