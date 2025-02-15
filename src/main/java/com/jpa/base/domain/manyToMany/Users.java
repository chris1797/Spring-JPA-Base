package com.jpa.base.domain.manyToMany;

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

    @ManyToMany
    @JoinTable(name = "users_products",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    // - @OneToMany(mappedBy = "member") : Order 엔티티의 member 필드에 의해 매핑된 것을 의미.
//    @OneToMany(mappedBy = "users")
//    private List<Orders2> orders = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        product.getUsers().add(this);
    }
}
