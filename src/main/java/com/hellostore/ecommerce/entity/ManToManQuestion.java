package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.ManToManQuestionType;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManToManQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mantoman_question_id")
    private Long id;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private ManToManQuestionType manToManQuestionType;

    @OneToOne(mappedBy = "manToManQuestion", fetch = LAZY)
    private ManToManAnswer manToManAnswer;

    @Builder
    public ManToManQuestion(String title, String content, ManToManQuestionType manToManQuestionType) {
        this.title = title;
        this.content = content;
        this.manToManQuestionType = manToManQuestionType;
    }
}
