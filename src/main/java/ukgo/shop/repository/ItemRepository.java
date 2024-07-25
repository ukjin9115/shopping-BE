package ukgo.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ukgo.shop.entity.Item;
public interface ItemRepository extends JpaRepository<Item, Integer> {


}
