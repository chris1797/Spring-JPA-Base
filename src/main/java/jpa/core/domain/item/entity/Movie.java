package jpa.core.domain.item.entity;

import jakarta.persistence.*;
import jpa.core.domain.item.dto.request.BookItemSaveRequest;
import jpa.core.domain.item.dto.request.MovieItemSaveRequest;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M")
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;


    public void updateItem(MovieItemSaveRequest updateRequest) {
        super.updateItemBasicInfo(
                updateRequest.name(),
                updateRequest.price(),
                updateRequest.stockQuantity()
        );

        this.director = updateRequest.director();
        this.actor = updateRequest.actor();

    }
}
