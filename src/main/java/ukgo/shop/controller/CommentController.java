package ukgo.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ukgo.shop.service.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public String postComment(@RequestParam String content, @RequestParam Integer parentId, @RequestParam String username) {
        commentService.saveComment(content, parentId, username);
        return "redirect:/detail/" + parentId;
    }
}
