package com.jpa.base.domain.compositeKey;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@IdClass(MusicalId.class)
public class Musical {

    @Id
    private String title;

    @Id
    private String actor;

}
