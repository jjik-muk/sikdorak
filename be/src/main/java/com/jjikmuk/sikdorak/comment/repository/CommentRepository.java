package com.jjikmuk.sikdorak.comment.repository;

import com.jjikmuk.sikdorak.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
