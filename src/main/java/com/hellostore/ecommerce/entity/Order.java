package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.DeliveryStatus;
import com.hellostore.ecommerce.enumType.OrderStatus;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name = "orders")
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    private String phoneNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public static Order createOrder(Optional<User> user, Delivery delivery,
                                    List<OrderProduct> orderProducts,
                                    PaymentMethodType paymentMethodType,
                                    PaymentStatus paymentStatus) {
        Order order = new Order();
        order.setUser(user.get());
        order.setDelivery(delivery);
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
        }
        order.setStatus(OrderStatus.BEFORE_CONFIRM);
        order.setPaymentStatus(paymentStatus);
        order.setPaymentMethodType(paymentMethodType);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }

        this.setStatus(OrderStatus.ORDER_CANCEL);
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderProduct orderProduct : orderProducts) {
            totalPrice += orderProduct.getTotalPrice();
        }
        return totalPrice;
    }
}
