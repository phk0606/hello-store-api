package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.FaqType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long id;

    private String question;
    private String answer;

    @Enumerated(EnumType.STRING)
    private FaqType faqType;

    @Builder
    public Faq(String question, String answer, FaqType faqType) {
        this.question = question;
        this.answer = answer;
        this.faqType = faqType;
    }
}
