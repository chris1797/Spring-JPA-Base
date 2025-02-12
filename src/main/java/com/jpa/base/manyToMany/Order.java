package com.jpa.base.manyToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Member member;

}
