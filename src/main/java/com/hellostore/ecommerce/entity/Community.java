package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    private String title;
    @Lob
    private String content;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<CommunityReply> replies = new ArrayList<>();

    @Builder
    public Community(User user, String content) {
        this.user = user;
        this.content = content;
    }
}
