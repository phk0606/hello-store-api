package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.DeliveryStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    private String recipientName;

    private String phoneNumber;

    private String requirement;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Builder
    public Delivery(Address address, DeliveryStatus status, String recipientName, String phoneNumber, String requirement) {
        this.address = address;
        this.status = status;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.requirement = requirement;
    }
}
