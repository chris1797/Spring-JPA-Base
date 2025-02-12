package com.jpa.base.manyToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private int stockAmount;
}
