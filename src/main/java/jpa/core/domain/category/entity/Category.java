package jpa.core.domain.category.entity;

import jpa.core.domain.item.entity.Item;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 다대다 관계는 중간 테이블이 필요하기 때문에 중간테이블이 자동으로 생성됨
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


    /*
     * 부모 입장에서 하위 카테고리 추가
     * + 자식 카테고리 입장에서 부모 설정
     */
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

    private void setParent(Category parent) {
        this.parent = parent;
    }

}
