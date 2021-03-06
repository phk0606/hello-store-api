package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.PointUseDetailType;
import com.hellostore.ecommerce.enumType.PointUseType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PointUseType pointUseType;
    @Enumerated(EnumType.STRING)
    private PointUseDetailType pointUseDetailType;
    private Integer point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public PointHistory(PointUseType pointUseType, PointUseDetailType pointUseDetailType, Integer point, User user) {
        this.pointUseType = pointUseType;
        this.pointUseDetailType = pointUseDetailType;
        this.point = point;
        this.user = user;
    }
}
