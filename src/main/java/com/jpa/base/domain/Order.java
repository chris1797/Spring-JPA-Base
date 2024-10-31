package com.jpa.base.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 어지간하면 해당 컬렉션은 인스턴스화 이후 변경하지 않는 것이 좋다.
     * 왜냐하면 하이버네이트는 컬렉션을 감싸서 내장 컬렉션을 사용하므로 컬렉션 변경 시 문제가 발생할 수 있다.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // ORDER, CANCEL

    /* ======================== 연관관계 메서드 ======================== */

    /**
     * 양방향 연관관계 편의 메서드, 양쪽에 모두 값을 설정해주는 것이 좋다.
     * @param member 회원 엔티티: Member
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    /**
     * @param orderItem 주문 상품: OrderItem
     */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    /**
     * @param delivery 배송 정보: Delivery
     */
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
