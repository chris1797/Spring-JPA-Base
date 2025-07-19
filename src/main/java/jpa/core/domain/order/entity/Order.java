package jpa.core.domain.order.entity;

import jpa.core.common.constants.DeliveryStatus;
import jpa.core.common.constants.OrderStatus;
import jakarta.persistence.*;
import jpa.core.domain.delivery.entity.Delivery;
import jpa.core.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // ORDER, CANCEL

    private LocalDateTime orderDate; // 주문 시간

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;



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
        //
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /* ======================== 비즈니스 로직 ======================== */

    /**
     * 주문 생성
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        // new Order(); 에서 생성된 orderItems에 orderItem들을 추가
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /**
     * 주문 취소 (재고수량을 원복), orderSatatus를 CANCEL로 변경
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /* ======================== 조회 로직 ======================== */
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

}
