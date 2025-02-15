package com.jpa.base.service;

import com.jpa.base.domain.item.Book;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() {
        // given
        Book book = em.find(Book.class, 1L);

        book.setName("updatedName");

        // when

        // then
    }
}
