package com.jpa.base.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorColumn(name = "B")
@Getter
@Setter
public class Book extends Item {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String author;
    private String isbn;
}
