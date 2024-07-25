package ukgo.shop.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDateTime;

@Controller
public class BasicController {
   @GetMapping("/")
   String hello(){
      return "redirect:/index.html";
   }

   @GetMapping("/date")
   @ResponseBody
   String date() {
      return LocalDateTime.now().toString();
   }

}
