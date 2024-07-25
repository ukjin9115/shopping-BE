package ukgo.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ukgo.shop.entity.Notification;
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
