package ukgo.shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ukgo.shop.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUsername(String username);
}
