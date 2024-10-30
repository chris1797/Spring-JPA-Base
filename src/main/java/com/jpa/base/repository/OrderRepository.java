package com.jpa.base.repository;

import com.jpa.base.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

}
