package co.kr.promptech.freeboard.repository;

import co.kr.promptech.freeboard.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
