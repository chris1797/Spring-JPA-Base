package com.jpa.base.domain;

import com.jpa.base.constants.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
}
