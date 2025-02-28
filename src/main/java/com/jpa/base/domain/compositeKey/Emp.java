package com.jpa.base.domain.compositeKey;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "emp")
@EntityListeners(AuditingEntityListener.class)
public class Emp {

    /*
     * @Embeddable 스타일
     * 복합키로 설정할 클래스를 @Embeddable로 정의하고 @EmbeddedId로 사용한다.
     */
    @EmbeddedId
    private EmpId empId;

    private String phone;

    @CreatedDate
    private LocalDateTime createAt;
}