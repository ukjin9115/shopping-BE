package ukgo.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ukgo.shop.entity.Comment;
import ukgo.shop.entity.Item;
import ukgo.shop.repository.CommentRepository;
import ukgo.shop.service.CommentService;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    public String postComment(@RequestParam String content, @RequestParam Integer parentId, @RequestParam String username) {
        commentService.saveComment(content, parentId, username);
        return "redirect:/detail/" + parentId;
    }
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer id, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            if (comment.getUsername().equals(auth.getName())) {
                commentRepository.deleteById(id);
                return ResponseEntity.status(200).body("삭제 완료");
            } else {
                return ResponseEntity.status(403).body("Forbidden");
            }
        } else {
            return ResponseEntity.status(404).body("Not Found");
        }
    }


}
