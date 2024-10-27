package com.jpa.base.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorColumn(name = "M")
@Getter
@Setter
public class Movie extends Item {

    @Id @GeneratedValue
    @Column(name = "movie_id")
    private Long id;

    private String director;
    private String actor;
}
