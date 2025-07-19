package jpa.core.domain.delivery.entity;

import jpa.core.common.constants.DeliveryStatus;
import jakarta.persistence.*;
import jpa.core.domain.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    /*
    EnumType.ORDINAL은 숫자로 저장됨, EnumType.STRING은 문자열로 저장됨
    EnumType.ORDINAL은 어지간하면 사용하지 않는 것이 좋다.
     */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // 배송 상태 [READY(준비), COMP(배송)]

    public void setAddress(Address address) {
        if (address.getAddress() == null) {
                throw new IllegalArgumentException("주소는 null이 될 수 없습니다.");
        }

        this.address = address;
    }

    public void setOrder(Order order) {
        // 기존 주문 정보가 있다면 기존 주문과의 연관관계를 해제
        if (this.order != null) {
            this.order.setDelivery(null);
        }

        // 현재 주문정보에 새로운 주문을 설정
        this.order = order;

        // 새로운 주문 정보가 있다면 새로운 주문정보로 연관관계 설정
        if (order != null) {
            order.setDelivery(this);
        }
    }
}
