package com.jpa.base.manyToMany;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "orders2")
public class Orders2 {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * - @ManyToMany 를 사용하면 자동으로 중간 테이블이 생성되면서 다대다 관계를 맺을 수 있다.
     * 하지만 실무에서는 @ManyToMany를 직접 사용하는 것 보다는 중간 테이블을 따로 엔티티를 만들어서 @OneToMany, @ManyToOne 으로 푸는 것을 더 권장된다.
     */

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int orderAmount;
}
