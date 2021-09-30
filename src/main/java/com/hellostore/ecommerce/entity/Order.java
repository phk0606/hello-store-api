package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.dto.OrderDto;
import com.hellostore.ecommerce.enumType.OrderDeliveryStatus;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import lombok.*;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDate;
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

    private LocalDateTime orderCancelDate;

    @Enumerated(EnumType.STRING)
    private OrderDeliveryStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Integer paymentPrice;

//    private String depositAccount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    private String depositorName;
    private LocalDate depositDueDate;

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
                                    OrderDto orderDto) {
        Order order = new Order();
        order.setUser(user.get());
        order.setDelivery(delivery);
        order.setPhoneNumber(orderDto.getPhoneNumber());
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
        }

        order.setStatus(OrderDeliveryStatus.BEFORE_CONFIRM);
        if (orderDto.getPaymentMethodType().equals(PaymentMethodType.WITHOUT_BANKBOOK)) {
            order.setPaymentStatus(PaymentStatus.BEFORE);
        } else {
            order.setPaymentStatus(PaymentStatus.FINISHED);
        }
        order.setPaymentMethodType(orderDto.getPaymentMethodType());
        order.setPaymentPrice(orderDto.getPaymentPrice());
//        order.setOrderDate(LocalDateTime.now());
        if(!ObjectUtils.isEmpty(orderDto.getDepositAccountId())) {

            order.setBankAccount(orderDto.getBankAccount());
            order.setDepositorName(orderDto.getDepositorName());
            order.setDepositDueDate(orderDto.getDepositDueDate());
        }
        return order;
    }

    public void cancel() {
        if (this.getStatus() == OrderDeliveryStatus.READY_SHIP
        || this.getStatus() == OrderDeliveryStatus.SHIPPING
        || this.getStatus() == OrderDeliveryStatus.COMPLETE_SHIP) {
            throw new IllegalStateException("이미 배송 준비, 배송 중, 배송 완료인 상품은 취소가 불가능 합니다.");
        }

        this.setStatus(OrderDeliveryStatus.ORDER_CANCEL);
        this.setPaymentStatus(
                this.paymentStatus.equals(PaymentStatus.BEFORE)
                        ? PaymentStatus.CANCEL_FINISHED : PaymentStatus.CANCEL_BEFORE);
        this.setOrderCancelDate(LocalDateTime.now());
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.cancel();
        }
    }

}
