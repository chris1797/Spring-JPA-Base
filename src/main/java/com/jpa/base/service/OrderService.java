package com.jpa.base.service;

import com.jpa.base.domain.Delivery;
import com.jpa.base.domain.Member;
import com.jpa.base.domain.Order;
import com.jpa.base.domain.OrderItem;
import com.jpa.base.domain.item.Item;
import com.jpa.base.repository.ItemRepository;
import com.jpa.base.repository.MemberRepository;
import com.jpa.base.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        /*
        주문 저장
        - Order 엔티티에 cascade = CascadeType.ALL 옵션이 있기 때문에 Order 엔티티만 저장해도 OrderItem, Delivery 엔티티도 함께 저장된다.
         */
        return orderRepository.save(order);
    }
}
