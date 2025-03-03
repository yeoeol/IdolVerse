package com.example.idolverse.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.idolverse.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
