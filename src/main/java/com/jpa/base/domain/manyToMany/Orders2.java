package com.jpa.base.domain.manyToMany;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders2")
public class Orders2 {

    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    /**
     * - @ManyToMany 를 사용하면 자동으로 중간 테이블이 생성되면서 다대다 관계를 맺을 수 있다.
     * 하지만 실무에서는 @ManyToMany를 직접 사용하는 것 보다는 중간 테이블을 따로 엔티티를 만들어서 @OneToMany, @ManyToOne 으로 푸는 것을 더 권장된다.
     * <p>
     * 이러면 중간 테이블을 엔티티로 승격시키는 것이다.
     * orderAmount 같은 컬럼이 추가되어도 User나 Product 엔티티에는 영향을 주지 않는다.
     */

    @ManyToOne
    @JoinColumn(name = "users_id") // 외래키를 지정하여 연관관계 설정
    private Users users;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int orderAmount;

}
