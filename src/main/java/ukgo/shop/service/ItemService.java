package ukgo.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukgo.shop.entity.Item;
import ukgo.shop.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, Integer price) {
        validateTitle(title);
        validatePrice(price);

        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        System.out.println(title);
        System.out.println(price);
        itemRepository.save(item);
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(Integer id) {
        Optional<Item> result = itemRepository.findById(id);
        return result.orElse(null);
    }
    // 아이템 업데이트
    public void updateItem(Integer id, String title, Integer price) {
        validateTitle(title);
        validatePrice(price);

        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setTitle(title);
            item.setPrice(price);
            itemRepository.save(item);
        } else {
            // 아이템이 존재하지 않는 경우 예외 처리
            throw new RuntimeException("Item not found");
        }
    }


    // 제목 유효성 검사
    private void validateTitle(String title) {
        if (title == null || title.length() > 20) {
            throw new IllegalArgumentException("Title must be between 1 and 20 characters.");
        }
    }

    // 가격 유효성 검사
    private void validatePrice(Integer price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be a non-negative integer.");
        }
    }

}
