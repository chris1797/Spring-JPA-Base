package com.jpa.base.manyToMany;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * - @ManyToMany 를 사용하면 중간 테이블을 사용하지 않고도 다대다 관계를 맺을 수 있다.
     * 하지만 실무에서는 중간 테이블을 사용하는 것을 권장한다.
     * 중간 테이블을 사용하면 추후 다대다 관계를 일대다, 다대일 관계로 변경할 때 유연하게 대처할 수 있다.
     * 또한, 중간 테이블에 추가적인 컬럼을 추가할 수 있어서 중간 테이블을 엔티티로 승격시킬 수 있다. ex) orderAmount
     * 중간 테이블을 엔티티로 승격하면 다대다 관계를 일대다, 다대일 관계로 변경할 때 테이블 구조를 유지할 수 있다.
     */

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int orderAmount;
}
