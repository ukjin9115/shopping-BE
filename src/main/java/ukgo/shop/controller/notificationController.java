package ukgo.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ukgo.shop.entity.Notification;
import ukgo.shop.entity.We;
import ukgo.shop.repository.NotificationRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@ToString
public class notificationController {
    private final NotificationRepository notificationRepository;

    @GetMapping("/notification")
    String list(Model model){
        List<Notification> result = notificationRepository.findAll();
        System.out.println(result.get(0).date);
//        var object = new We();
//        object.나이설정(12);
//        System.out.println(object[0]);
//        var object = new We();
//        object.setAge(30);
//        object.setName("김욱진");
//        object.나이설정(-10);
//        object.한살더하기();
//        System.out.println(object.getAge());

        model.addAttribute("Notifications", result);
        return "notification.html";
    }

}