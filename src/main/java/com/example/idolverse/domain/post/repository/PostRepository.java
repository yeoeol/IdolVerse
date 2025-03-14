package com.example.idolverse.domain.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findPostsByCommunity(Community community);
}
