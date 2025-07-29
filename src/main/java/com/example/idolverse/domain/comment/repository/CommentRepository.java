package com.example.idolverse.domain.comment.repository;

import com.example.idolverse.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
