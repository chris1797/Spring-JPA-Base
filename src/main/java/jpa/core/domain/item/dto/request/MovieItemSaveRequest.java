package jpa.core.domain.item.dto.request;

import jpa.core.common.constants.ItemDtype;

public record MovieItemSaveRequest(
        String name,
        int price,
        int stockQuantity,

        ItemDtype dtype, // ItemDtype은 enum 타입으로, 아이템의 종류를 나타냄

        String director,
        String actor

) {

}
