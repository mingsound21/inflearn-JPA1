package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") // 연관관계의 주인
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // 연관관계의 주인 X
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") // 연관관계 주인
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // enum type 무조건 STRING으로
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    //== 연관관계 편의 메서드==//
    // 연관관계의 주인 쪽에 작성이 아닌, 비즈니스 중심이 되는 곳에 작성
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    // orderItem이 연관관계의 주인이지만, 비즈니스 중심이 order에서 orderItem을 추가하는 것이므로
    // order.addOrderItem을 호출하는 게 맞음
    // 해당 연관관계 편의 메소드 내부에 orderItem.setOrder로 fk에 값이 설정됨
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
