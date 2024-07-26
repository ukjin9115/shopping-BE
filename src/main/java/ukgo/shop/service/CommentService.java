package ukgo.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukgo.shop.entity.Comment;
import ukgo.shop.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> findCommentsByParentId(Integer parentId) {
        return commentRepository.findByParentId(parentId);
    }

    public void saveComment(String content, Integer parentId, String username) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setParentId(parentId);
        comment.setUsername(username);
        commentRepository.save(comment);
    }
}
