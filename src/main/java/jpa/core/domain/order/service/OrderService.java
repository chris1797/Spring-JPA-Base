package jpa.core.domain.order.service;

import jpa.core.domain.delivery.entity.Delivery;
import jpa.core.domain.member.entity.Member;
import jpa.core.domain.order.entity.Order;
import jpa.core.domain.order.entity.OrderItem;
import jpa.core.domain.item.entity.Item;
import jpa.core.domain.item.repository.ItemRepository;
import jpa.core.domain.member.repository.MemberRepository;
import jpa.core.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public Order order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        /*
        주문 저장
        - Order 엔티티에 cascade = CascadeType.ALL 옵션이 있기 때문에 Order 엔티티만 저장해도 OrderItem, Delivery 엔티티도 함께 저장된다.
        - 현재 비즈니스 로직에는 Delivery, OrderItem이 Order만 참조하고 있기 때문에 CascadeType.ALL 옵션을 사용해도 문제가 없지만,
        - 실제로는 Delivery, OrderItem이 다른 엔티티를 참조하고 있을 수 있기 때문에 CascadeType.ALL 옵션을 사용하는 것은 위험하다.
         */
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        order.cancel();
    }

    public List<Order> findOrders(Long memberId) {
        return orderRepository.findByMember_id(memberId);
    }


//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAllByString(orderSearch);
//    }
}
