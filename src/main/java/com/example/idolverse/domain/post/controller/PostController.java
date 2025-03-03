package com.example.idolverse.domain.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.post.dto.PostRequestDto;
import com.example.idolverse.domain.post.dto.PostResponseDto;
import com.example.idolverse.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/{urlPath}/feed/posts")
	public ResponseEntity<?> post(@PathVariable String urlPath, @RequestBody PostRequestDto requestDto) {
		PostResponseDto responseDto = postService.post(urlPath, requestDto.content());
		return ResponseEntity.ok(responseDto);
	}
}
