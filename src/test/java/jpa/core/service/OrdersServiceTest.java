package jpa.core.service;

import jpa.core.api.member.request.MemberJoinRequest;
import jpa.core.common.constants.OrderStatus;
import jpa.core.common.converter.MemberConverter;
import jpa.core.domain.delivery.entity.Address;
import jpa.core.domain.delivery.entity.Delivery;
import jpa.core.domain.item.entity.Album;
import jpa.core.domain.item.entity.Item;
import jpa.core.common.exception.NotEnoughStockException;
import jpa.core.domain.item.service.ItemService;
import jpa.core.domain.member.entity.Member;
import jpa.core.domain.member.service.MemberService;
import jpa.core.domain.order.entity.Order;
import jpa.core.domain.order.entity.OrderItem;
import jpa.core.domain.order.service.OrderService;
import jpa.core.domain.order.repository.OrderRepositoryLegacy;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Autowired private OrderRepositoryLegacy orderRepository;


    @DisplayName("테스트용 주문 데이터 생성")
    @Test
    @Rollback(false)
    void createOrder() {
        // given
//        Member member = createMember("회원1", new Address("서울시 관악구", "문성로 123-123"));

        Member member = memberService.fetchMember(2L);

        Item item = createItem("앨범1", 10000, 10);

        int orderCount = 2;

        // when
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderCount);

        for (int i = 1; i <= 10; i++) {
            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem);
            orderRepository.save(order);
        }

    }

    @DisplayName("주문 테스트")
    @Test
    void order() {
        // given
        Member member = createMember("회원1", new Address("서울시 관약구", "문성로 123-123"));
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
        MemberJoinRequest request = new MemberJoinRequest("회원1", null, "서울시 관악구", "문성로 123-123");
        Member joinedMember = memberService.join(request);

        Item item = createItem("앨범1", 10000, 10);
        itemService.save(item);
        int orderCount = 11; // 재고 수량 초과

        // when¸
        assertThrows(NotEnoughStockException.class, () -> orderService.order(joinedMember.getId(), item.getId(), orderCount));
    }

    @DisplayName("주문 취소 테스트")
    @Test
    void cancelOrder() {
        // given
        Member member = createMember("회원1", new Address("서울시 관악구", "문성로 123-123"));
        Item item = createItem("앨범1", 10000, 10);

        int orderCount = 2;
        Order order = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(order.getId());

        // then
        Order findOrder = orderRepository.findOne(order.getId());
        assertEquals(OrderStatus.CANCEL, findOrder.getStatus());
        assertEquals(item.getStockQuantity(), 10);

    }

    @DisplayName("회원 주문목록 테스트")
    @Test
    void memberOrdersTest() {
        // given
        List<Order> orders = orderService.findOrders(2L);

        for (Order order : orders) {
            System.out.println("order = " + order.getId() + " / " + order.getOrderDate());
        }

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