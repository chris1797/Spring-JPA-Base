package com.jpa.base.domain.manyToMany;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private int price;

    private int stockAmount;

    // Users의 products 필드에 의해 매핑 (Users가 주인)
    @ManyToMany(mappedBy = "products")
    private List<Users> users = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addUser(Users user) {
        users.add(user);
        user.getProducts().add(this);
    }
}
