package com.jpa.base.service;

import com.jpa.base.domain.*;
import com.jpa.base.domain.item.Album;
import com.jpa.base.domain.item.Item;
import com.jpa.base.exception.NotEnoughStockException;
import com.jpa.base.repository.MemberRepository;
import com.jpa.base.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired private EntityManager em;
    @Autowired private OrderService orderService;
    @Autowired private MemberService memberService;
    @Autowired private ItemService itemService;
    @Autowired private OrderRepository orderRepository;


    @DisplayName("주문 테스트")
    @Test
    void order() {
        // given
        Member member = createMember("회원1", new Address("서울", "문성로", "123-123"));
        Item item = createItem("앨범1", 10000, 10);
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
        Member member = createMember("회원1", new Address("서울", "문성로", "123-123"));
        Long joinedId = memberService.join(member);

        Item item = createItem("앨범1", 10000, 10);
        itemService.save(item);
        int orderCount = 11; // 재고 수량 초과

        // when¸
        assertThrows(NotEnoughStockException.class, () -> orderService.order(joinedId, item.getId(), orderCount));
    }

    @DisplayName("주문 취소 테스트")
    @Test
    void cancelOrder() {
        // given
        Member member = createMember("회원1", new Address("서울", "문성로", "123-123"));
        Item item = createItem("앨범1", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, findOrder.getStatus());
        assertEquals(item.getStockQuantity(), 10);

    }


    /* ========================== Private Method ========================== */
    private static Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        return delivery;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);

        em.persist(member);
        return member;
    }

    private Item createItem(String name, int price, int stockQuantity) {
        Item item = new Album();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);

        em.persist(item);
        return item;
    }



}