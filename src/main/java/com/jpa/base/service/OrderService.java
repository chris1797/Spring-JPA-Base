package com.jpa.base.service;

import com.jpa.base.domain.Order;
import com.jpa.base.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    @Transactional
    public Long save(Order order) {
        orderRepository.save(order);
        return order.getId();
    }
}
