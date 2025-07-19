package jpa.core.domain.item.service;

import jpa.core.domain.item.dto.request.AlbumItemSaveRequest;
import jpa.core.domain.item.dto.request.BookItemSaveRequest;
import jpa.core.domain.item.dto.request.MovieItemSaveRequest;
import jpa.core.domain.item.entity.Album;
import jpa.core.domain.item.entity.Book;
import jpa.core.domain.item.entity.Item;
import jpa.core.domain.item.entity.Movie;
import jpa.core.domain.item.repository.ItemRepository;
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
    public void updateBook(Long itemId, BookItemSaveRequest saveRequest) {
        Book findBook = (Book) findOne(itemId);
        findBook.updateItem(saveRequest);
    }

    @Transactional
    public void updateAlbum(Long itemId, AlbumItemSaveRequest saveRequest) {
        Album findAlbum = (Album) findOne(itemId);
        findAlbum.updateItem(saveRequest);
    }

    @Transactional
    public void updateMovie(Long itemId, MovieItemSaveRequest saveRequest) {
        Movie findMovie = (Movie) findOne(itemId);
        findMovie.updateItem(saveRequest);
    }

    private Item findOne(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

}
