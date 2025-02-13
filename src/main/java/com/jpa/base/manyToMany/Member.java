package com.jpa.base.manyToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    // - @OneToMany(mappedBy = "member") : Order 엔티티의 member 필드에 의해 매핑된 것을 의미.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
