package com.jpa.base.domain.compositeKey;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable // 복합키로 설정
public class EmpId implements Serializable {
    /*
     * 복합키로 설정하려면 반드시 Serializable을 구현해야 한다.
     * 아니면 org.hibernate.MappingException: Could not determine type for: com.jpa.base.domain.compositeKey.EmpId, at table: emp, for columns: [org.hibernate.mapping.Column(emp_id)]
     * 에러가 발생한다.
     */
    private String empName;
    private Integer empNo;

}