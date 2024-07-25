package ukgo.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukgo.shop.entity.Item;
import ukgo.shop.repository.ItemRepository;
import ukgo.shop.service.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;
//깃허브테스트 0726 새벽0308
    @GetMapping("/list")
    public String list(Model model){
        List<Item> result = itemService.findAllItems();
        model.addAttribute("items", result);
        return "list.html";
    }
    @GetMapping("/write")
    public String write(Model model){

        return "write.html";
    }
    @GetMapping("/change/{id}")
    public String change(@PathVariable("id") Integer id, Model model) {
        Item item = itemService.findItemById(id);
        if (item != null) {
            model.addAttribute("item", item);
            return "change.html";
        } else {
            return "error.html";
        }

    }

    // 수정 폼 제출 처리 (수정된 Item을 저장)
    @PostMapping("/update")
    public String update(@RequestParam Integer id, @RequestParam String title, @RequestParam Integer price) {
        itemService.updateItem(id, title, price);
        return "redirect:/list";
    }

    @PostMapping("/add")
    public String writePost(@RequestParam String title, @RequestParam Integer price) {

        itemService.saveItem(title, price);
        return "redirect:/list";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model){

        Item item = itemService.findItemById(id);
        if (item != null) {
            model.addAttribute("item", item);
            return "detail.html";
        } else {
            return "error.html";
        }
    }
    // 삭제 메서드

    @DeleteMapping("/item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }



}