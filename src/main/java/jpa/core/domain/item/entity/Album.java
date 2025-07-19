package jpa.core.domain.item.entity;

import jakarta.persistence.*;
import jpa.core.domain.item.dto.request.AlbumItemSaveRequest;
import jpa.core.domain.item.dto.request.BookItemSaveRequest;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter
public class Album extends Item {

    private String artist;
    private String etc;


    public void updateItem(AlbumItemSaveRequest saveRequest) {
        super.updateItemBasicInfo(
                saveRequest.name(),
                saveRequest.price(),
                saveRequest.stockQuantity()
        );

        this.artist = saveRequest.artist();
        this.etc = saveRequest.etc();
    }
}
