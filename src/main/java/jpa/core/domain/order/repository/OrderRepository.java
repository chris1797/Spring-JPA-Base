package jpa.core.domain.order.repository;

import jpa.core.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMember_id(Long memberId);

//    List<Order> findAllByString(OrderSearch orderSearch);
}
