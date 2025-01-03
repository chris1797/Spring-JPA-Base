package com.jpa.base.repository;

import com.jpa.base.domain.Order;
import com.jpa.base.dto.OrderSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMember_id(Long memberId);

//    List<Order> findAllByString(OrderSearch orderSearch);
}
