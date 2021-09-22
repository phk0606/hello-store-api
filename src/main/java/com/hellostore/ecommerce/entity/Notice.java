package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    private boolean important;

    @Builder
    public Notice(String title, String content, boolean important) {
        this.title = title;
        this.content = content;
        this.important = important;
    }
}
