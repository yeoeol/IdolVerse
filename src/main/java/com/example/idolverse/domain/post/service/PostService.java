package com.example.idolverse.domain.post.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.post.dto.PostResponseDto;
import com.example.idolverse.domain.post.entity.Post;
import com.example.idolverse.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public PostResponseDto post(String urlPath, String content) {
		// Community community = communityService.findByUrlPath(urlPath);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member member = (Member)authentication.getPrincipal();
		Post post = Post.builder()
			.content(content)
			.member(member)
			// .community(community)
			.build();

		Post savedPost = postRepository.save(post);
		return PostResponseDto.from(savedPost);
	}
}
