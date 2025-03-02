package jpa.core.domain.manyToMany;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue
    @Column(name = "users_id")
    public Long id;

    public String name;

    // @OneToMany(mappedBy = "users") : Order 엔티티의 users 필드에 의해 매핑
    @OneToMany(mappedBy = "users")
    private List<Orders2> orders = new ArrayList<>();


    public void addOrder(Orders2 order) {
        orders.add(order);
        order.setUsers(this);
    }

}
