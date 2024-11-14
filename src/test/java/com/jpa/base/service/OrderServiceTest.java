package com.jpa.base.service;

import com.jpa.base.domain.*;
import com.jpa.base.domain.item.Album;
import com.jpa.base.domain.item.Item;
import com.jpa.base.exception.NotEnoughStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrderServiceTest {

    @DisplayName("주문 테스트")
    @Test
    void order() {
        // given
        Member member = createMember();
        Item item = createItem();
        Delivery delivery = createDelivery(member);

        int orderCount = 2;

        // when
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderCount);
        Order order = Order.createOrder(member, delivery, orderItem);

        // then
        assertEquals(OrderStatus.ORDER, order.getStatus());
        assertEquals(order.getMember().getName(), "회원1");
        assertEquals(order.getOrderItems().size(), 1);
        assertEquals(order.getDelivery().getAddress(), member.getAddress());

    }

    @DisplayName("재고 수량 초과 테스트")
    @Test
    public void overQuantity() {
        // given
        Item item = createItem();

        // when
        int orderCount = 11; // 재고 수량 초과

        // then
        assertThrows(NotEnoughStockException.class, () -> {
            OrderItem.createOrderItem(item, item.getPrice(), orderCount);
        });
    }


    /* ========================== Private Method ========================== */
    private static Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        return delivery;
    }

    private static Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "문성로", "123-123"));
        return member;
    }

    private static Item createItem() {
        Item item = new Album();
        item.setName("앨범1");
        item.setPrice(10000);
        item.setStockQuantity(10);
        return item;
    }


//    @Test
//    void cancelOrder() {
//        // given
//
//
//
//
//    }

}