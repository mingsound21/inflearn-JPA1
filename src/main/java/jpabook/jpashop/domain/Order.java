package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") // 연관관계의 주인
    private Member member;

    @OneToMany(mappedBy = "order") // 연관관계의 주인 X
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id") // 연관관계 주인
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // enum type 무조건 STRING으로
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]
}
