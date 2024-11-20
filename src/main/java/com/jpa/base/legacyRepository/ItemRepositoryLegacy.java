package com.jpa.base.legacyRepository;

import com.jpa.base.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryLegacy {

    private final EntityManager em;


    public void save(Item item) {
        // id가 없으면 신규 등록, id가 있으면 update
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item); // 강제 업데이트
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
