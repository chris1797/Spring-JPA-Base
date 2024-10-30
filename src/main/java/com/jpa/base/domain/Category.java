package com.jpa.base.domain;

import com.jpa.base.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 다대다 양방향 매핑: 중간 테이블이 필요하다.
    // 다대다 양방향 매핑은 운영 레벨에서 사용하지 않는 것이 좋다.
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent") // 셀프 양방향 매핑
    private List<Category> child = new ArrayList<>();

}
