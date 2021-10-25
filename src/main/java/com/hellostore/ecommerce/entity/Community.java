package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    private String title;
    @Lob
    private String content;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<CommunityReply> replies = new ArrayList<>();

    @Builder
    public Community(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
