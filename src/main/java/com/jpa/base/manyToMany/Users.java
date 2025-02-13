package com.jpa.base.manyToMany;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue
    @Column(name = "users_id")
    public Long id;

    public String name;

    // - @OneToMany(mappedBy = "member") : Order 엔티티의 member 필드에 의해 매핑된 것을 의미.
    @OneToMany(mappedBy = "users")
    private List<Orders2> orders = new ArrayList<>();
}
