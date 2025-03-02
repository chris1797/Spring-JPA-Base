package jpa.core.service;

import jpa.core.domain.item.entity.Book;
import jpa.core.domain.item.entity.Item;
import jakarta.persistence.EntityManager;
import jpa.core.domain.item.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Autowired
    ItemService itemService;

    @Test
    public void updateTest() {
        // given
        Book book = em.find(Book.class, 1L);
        book.setName("updatedName");

        // when

        // then
    }

    @Test
    @DisplayName("상품 등록 및 조회 테스트")
    @Rollback(value = false)
    public void saveTest() {
        // given
        Book book = new Book();
        book.setName("JPA");
        book.setAuthor("이재훈");

        // when
        em.persist(book);

        // then
        Book findBook = em.find(Book.class, book.getId());
        System.out.println("findBook = " + findBook.toString());

        List<Item> items = itemService.findItems();
        for (Item item : items) {
            System.out.println("item = " + item.toString());
        }
    }
}
