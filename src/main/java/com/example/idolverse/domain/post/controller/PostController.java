package com.example.idolverse.domain.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.post.dto.FeedTabPostsInfoDto;
import com.example.idolverse.domain.post.dto.PostRequestDto;
import com.example.idolverse.domain.post.dto.PostResponseDto;
import com.example.idolverse.domain.post.service.PostService;
import com.example.idolverse.global.common.entity.CustomMemberDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Post-Controller", description = "특정 커뮤니티 게시글 관련 API")
@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@Operation(summary = "특정 커뮤니티 게시글 작성")
	@PostMapping("/{urlPath}/feed/posts")
	public ResponseEntity<PostResponseDto> newPost(
		@PathVariable String urlPath,
		@RequestBody PostRequestDto requestDto,
		@AuthenticationPrincipal CustomMemberDetails customMemberDetails
	) {
		PostResponseDto responseDto = postService.post(urlPath, requestDto, customMemberDetails);
		return ResponseEntity.ok(responseDto);
	}

	@Operation(summary = "특정 커뮤니티 전체 게시글 조회")
	@GetMapping("/{urlPath}/feed")
	public ResponseEntity<FeedTabPostsInfoDto> getPostsByUrlPath(
		@PathVariable String urlPath
	) {
		FeedTabPostsInfoDto feedTabPostsInfoDto = postService.getAllPosts(urlPath);
		return ResponseEntity.ok(feedTabPostsInfoDto);
	}
}
