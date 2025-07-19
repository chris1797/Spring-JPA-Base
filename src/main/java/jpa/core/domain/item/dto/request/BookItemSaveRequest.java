package jpa.core.domain.item.dto.request;

import jpa.core.common.constants.ItemDtype;

public record BookItemSaveRequest(
        String name,
        int price,
        int stockQuantity,

        ItemDtype dtype,

        String author,
        String isbn
) {

}
