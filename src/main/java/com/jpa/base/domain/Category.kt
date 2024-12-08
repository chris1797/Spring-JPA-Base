package com.jpa.base.domain

import jakarta.persistence.*

@Table(name = "category")
@Entity
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0;
    var name: String? = null;
}