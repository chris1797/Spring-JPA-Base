package com.jpa.base.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorColumn(name = "A")
@Getter
@Setter
public class Album {

    @Id
    @GeneratedValue
    @Column(name = "album_id")
    private Long id;

    private String artist;
    private String etc;

}
