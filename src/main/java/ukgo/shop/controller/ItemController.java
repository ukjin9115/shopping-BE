package ukgo.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import ukgo.shop.entity.Item;
import ukgo.shop.entity.Member;
import ukgo.shop.entity.Comment;
import ukgo.shop.service.ItemService;
import ukgo.shop.service.MemberService;
import ukgo.shop.service.CommentService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;
    private final CommentService commentService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 3; // 페이지당 아이템 수
        Page<Item> result = itemService.findPageBy(page, pageSize);
        model.addAttribute("items", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        return "list.html";
    }

    @GetMapping("/write")
    public String write(Model model, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        return "write.html";
    }

    @PostMapping("/add")
    public String writePost(@RequestParam String title, @RequestParam Integer price, @RequestParam("file") MultipartFile file, Authentication auth) throws IOException {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        Member member = memberService.findMemberByUsername(auth.getName());
        itemService.saveItem(title, price, file, member);
        return "redirect:/list";
    }

    @GetMapping("/change/{id}")
    public String change(@PathVariable("id") Integer id, Model model, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        Item item = itemService.findItemById(id);
        if (item != null && item.getMember().getUsername().equals(auth.getName())) {
            model.addAttribute("item", item);
            return "change.html";
        } else {
            return "error.html";
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id, @RequestParam String title, @RequestParam Integer price, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        Item item = itemService.findItemById(id);
        if (item != null && item.getMember().getUsername().equals(auth.getName())) {
            itemService.updateItem(id, title, price);
            return "redirect:/list";
        } else {
            return "error.html";
        }
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer id, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Item item = itemService.findItemById(id);
        if (item != null && item.getMember().getUsername().equals(auth.getName())) {
            itemService.deleteItem(id);
            return ResponseEntity.status(200).body("삭제 완료");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model, Authentication auth) {
        Item item = itemService.findItemById(id);
        if (item != null) {
            List<Comment> comments = commentService.findCommentsByParentId(id);
            model.addAttribute("item", item);
            model.addAttribute("comments", comments);
            if (auth != null && auth.isAuthenticated()) {
                model.addAttribute("username", auth.getName());
            }
            return "detail.html";
        } else {
            return "error.html";
        }
    }

    @GetMapping("/list/page/{id}")
    public String getListPage(Model model, @PathVariable Integer id) {
        Page<Item> result = itemService.findPageBy(id, 3);
        model.addAttribute("items", result);
        return "list.html";
    }
}
