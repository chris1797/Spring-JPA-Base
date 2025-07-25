package jpa.core.domain.item.entity;

import jpa.core.common.constants.ItemDtype;
import jpa.core.domain.category.entity.Category;
import jpa.core.common.exception.NotEnoughStockException;
import jakarta.persistence.*;
import jpa.core.domain.item.dto.request.BookItemSaveRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략
@DiscriminatorColumn(name = "dtype") // 구분 컬럼
@Builder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private ItemDtype dtype; // 상품 타입 (책, 앨범, 영화 등)

    private String name; // 이름
    private int price; // 가격
    private int stockQuantity; // 재고 수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /* =================================== 비즈니스 로직 =================================== */

    /**
     * 상품 재고 증가
     *
     * @param quantity :int 증가할 재고 수량
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 상품 재고 감소
     *
     * @param quantity :int 감소할 재고 수량
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }

    protected void updateItemBasicInfo(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static Item of(String name, int price, int stockQuantity, ItemDtype dtype) {
        return Item.builder()
                .dtype(dtype)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }

}
