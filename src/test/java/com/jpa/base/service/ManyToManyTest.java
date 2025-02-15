package com.jpa.base.service;

import com.jpa.base.domain.manyToMany.Product;
import com.jpa.base.domain.manyToMany.Users;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
public class ManyToManyTest {

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    public void userAddTest() {
        // given
        Users user1 = new Users();
        user1.setName("chris");
        em.persist(user1);

        Product product1 = new Product();
        product1.setName("product1");
        em.persist(product1);

        // when
        user1.addProduct(product1);
        product1.addUser(user1);

        // then
        for (Product product : user1.getProducts()) {
            System.out.println("product = " + product.getName());
        }
    }
}
