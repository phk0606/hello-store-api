package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @OneToMany(mappedBy = "productComment", cascade = CascadeType.ALL)
    private List<ProductCommentImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    private String content;
    private int grade;

    @OneToMany(mappedBy = "productComment", cascade = CascadeType.ALL)
    private List<ProductCommentReply> replies = new ArrayList<>();

    @Builder
    public ProductComment(User user, OrderProduct orderProduct, String content, int grade) {
        this.user = user;
        this.orderProduct = orderProduct;
        this.content = content;
        this.grade = grade;
    }
}
