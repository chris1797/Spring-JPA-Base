package com.jpa.base.service;

import com.jpa.base.domain.item.Book;
import com.jpa.base.domain.item.Item;
import com.jpa.base.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book bookParam) {
        // itemId로 영속상태의 엔티티를 가져온다. 이제부터 이 친구는 영속성 컨텍스트에서 관리된다.
        // 이 상태에서 값을 변경하면 트랜잭션이 커밋되는 시점에 변경된 값을 DB에 반영한다.
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(bookParam.getName());
        findItem.setPrice(bookParam.getPrice());
        findItem.setStockQuantity(bookParam.getStockQuantity());
        // 이렇게 변경된 값은 트랜잭션이 커밋되는 시점에 변경된 값을 DB에 반영한다.

        /*
        merge 를 사용하는 방법, merge 는 결국 위의 과정을 한번에 처리해서 리턴해주는 메서드이다.
        병합(merge)은 준영속 엔티티를 영속 엔티티로 만들어주는 역할을 한다.
        준영속 엔티티는 영속성 컨텍스트가 관리하지 않는 엔티티를 말한다.

        병합 시 값이 만약 null 이라면, null 로 업데이트가 되어버린다.
        ㄴ 때문에 실무에서는 merge 보다는 위의 변경감지(더티체킹) 방식을 사용하는 것을 권장한다.
        */
        Book book = new Book();
        book.setId(findItem.getId()); // Id를 넣어줬다는건, 이 엔티티는 이미 DB에 저장된 엔티티라는 뜻이다.
        book.setName(findItem.getName());
        book.setPrice(findItem.getPrice());
        book.setStockQuantity(findItem.getStockQuantity());

        itemRepository.save(book);

    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

}
