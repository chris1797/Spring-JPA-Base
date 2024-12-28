package com.jpa.base.domain;

import com.jpa.base.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "order_item")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // createOrderItem() 로만 인스턴스화 하도록 생성자 접근 제한
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문 상품

    private int orderPrice; // 주문 가격

    private int count; // 주문 수량

    /**
     * 주문 취소 시 재고수량 원복
     */
    public void cancel() {
        getItem().addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    /* ======================== 생성 메서드 ======================== */
    /**
     * 주문 상품 생성
     * @param item 상품 엔티티: Item
     * @param orderPrice 주문 가격
     * @param count 주문 수량
     * @return OrderItem 주문 상품 엔티티
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);

        // item에 가격이 있지만 주문 가격을 따로 저장하는 이유는 가격이 변동될 수 있기 때문이다.
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

}
