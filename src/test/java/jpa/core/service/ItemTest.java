package jpa.core.service;

import jpa.core.domain.item.entity.Item;

public class ItemTest {

    public static Item createItem(String name, int price, int stockQuantity) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        return item;
    }
}
