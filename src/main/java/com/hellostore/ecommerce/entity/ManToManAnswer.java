package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManToManAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mantoman_answer_id")
    private Long id;

    private String content;

    @OneToOne
    @JoinColumn(name = "mantoman_question_id")
    private ManToManQuestion manToManQuestion;

    @Builder
    public ManToManAnswer(String content, ManToManQuestion manToManQuestion) {
        this.content = content;
        this.manToManQuestion = manToManQuestion;
    }
}
