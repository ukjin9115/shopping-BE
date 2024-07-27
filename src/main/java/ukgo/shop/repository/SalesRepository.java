package ukgo.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ukgo.shop.entity.Sales;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
    @Query(value = "SELECT s FROM Sales s JOIN FETCH s.member")
    List<Sales> customFindAll();
}
