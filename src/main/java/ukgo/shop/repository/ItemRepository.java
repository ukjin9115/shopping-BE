package ukgo.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ukgo.shop.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
   Page<Item> findPageBy(Pageable page);

   @Query(value = "SELECT * FROM shop.item WHERE MATCH(title) AGAINST(?1)",
           countQuery = "SELECT count(*) FROM shop.item WHERE MATCH(title) AGAINST(?1)",
           nativeQuery = true)
   Page<Item> searchItems(String text, Pageable pageable);
}

