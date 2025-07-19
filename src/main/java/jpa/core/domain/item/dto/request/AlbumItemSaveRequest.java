package jpa.core.domain.item.dto.request;

import jpa.core.common.constants.ItemDtype;

public record AlbumItemSaveRequest(
        String name,
        int price,
        int stockQuantity,

        ItemDtype dtype, // ItemDtype은 enum 타입으로, 아이템의 종류를 나타냄

        String artist, // 아티스트 이름
        String etc // 기타 정보 (예: 레이블, 발매일 등)
) {

}
