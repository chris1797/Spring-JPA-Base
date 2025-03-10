package jpa.core.domain.order.repository;

import jpa.core.domain.order.entity.Order;
import jpa.core.api.request.OrderSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryLegacy {

    @PersistenceContext
    private EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m" +
                " where o.status = :status" +
                " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .getResultList();
    }



}
