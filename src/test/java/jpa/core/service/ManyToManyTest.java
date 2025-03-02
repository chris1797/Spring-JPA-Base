package jpa.core.service;

import jpa.core.domain.manyToMany.Orders2;
import jpa.core.domain.manyToMany.Product;
import jpa.core.domain.manyToMany.Users;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("@ManyToMany 양방향 매핑 테스트")
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
//        user1.addProduct(product1);
//        product1.addUser(user1);

        // then
//        for (Product product : user1.getProducts()) {
//            System.out.println("product = " + product.getName());
//        }
    }

    @DisplayName("@ManyToMany 양방향을 @ManyToOne, @OneToMany로 변경 후 테스트")
    @Test
    @Rollback(false)
    public void orderTest() {
        // given
        Users user1 = new Users();
        user1.setName("chris");
        em.persist(user1);

        Product product1 = new Product();
        product1.setName("product1");
        em.persist(product1);

        Orders2 order = new Orders2();
        order.setUsers(user1);
        order.setProduct(product1);
        order.setOrderAmount(2);
        em.persist(order);

        // when

    }
}
