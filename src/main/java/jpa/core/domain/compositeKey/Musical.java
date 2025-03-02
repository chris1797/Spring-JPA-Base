package jpa.core.domain.compositeKey;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@IdClass(MusicalId.class) // 복합키 설정: @IdClass로 지정한 클래스의 필드들로 복합키를 설정한다.
public class Musical {

    @Id
    @Column(name = "musical_title")
    private String title; // IdClass의 필드와 이름이 같아야 한다.

    @Id
    @Column(name = "musical_actor")
    private String actor;

}
