package ukgo.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ukgo.shop.entity.Item;
import ukgo.shop.entity.Member;
import ukgo.shop.repository.ItemRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private static final String UPLOAD_DIR = "C:/shopupload/";

    public void saveItem(String title, Integer price, MultipartFile file, Member member) throws IOException {
        validateTitle(title);
        validatePrice(price);

        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setMember(member);

        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);
            item.setFilePath(fileName);
        }

        itemRepository.save(item);
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(Integer id) {
        Optional<Item> result = itemRepository.findById(id);
        return result.orElse(null);
    }

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
            throw new RuntimeException("Item not found");
        }
    }
    public Page<Item> searchItems(String searchText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return itemRepository.searchItems(searchText, pageable); // 검색 메서드 호출
    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }

    public Page<Item> findPageBy(int page, int pageSize) {
        return itemRepository.findPageBy(PageRequest.of(page - 1, pageSize));
    }

    private void validateTitle(String title) {
        if (title == null || title.length() > 20) {
            throw new IllegalArgumentException("Title must be between 1 and 20 characters.");
        }
    }

    private void validatePrice(Integer price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be a non-negative integer.");
        }
    }
}
