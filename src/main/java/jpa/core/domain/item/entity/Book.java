package jpa.core.domain.item.entity;

import jakarta.persistence.*;
import jpa.core.domain.item.dto.request.BookItemSaveRequest;
import lombok.Getter;

@Entity
@DiscriminatorValue("B")
@Getter
public class Book extends Item {

    private String author;
    private String isbn;

    public String toString() {
        return "Book {" +
                "author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    public void updateItem(BookItemSaveRequest updateRequest) {
        super.updateItemBasicInfo(
                updateRequest.name(),
                updateRequest.price(),
                updateRequest.stockQuantity()
        );

        this.author = updateRequest.author();
        this.isbn = updateRequest.isbn();
    }

}
