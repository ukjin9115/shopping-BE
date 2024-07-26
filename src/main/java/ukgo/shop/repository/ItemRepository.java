package ukgo.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ukgo.shop.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
   Page<Item> findPageBy(Pageable page);

}
