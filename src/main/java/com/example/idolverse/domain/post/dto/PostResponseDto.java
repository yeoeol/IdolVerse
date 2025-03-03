package com.example.idolverse.domain.post.dto;

import com.example.idolverse.domain.post.entity.Post;

import lombok.Builder;

@Builder
public record PostResponseDto(
	Long postId,
	String content,
	Long memberId,
	Long communityId
) {
	public static PostResponseDto from(Post post) {
		return PostResponseDto.builder()
			.postId(post.getPostId())
			.content(post.getContent())
			.memberId(post.getMember().getMemberId())
			.communityId(post.getCommunity().getCommunityId())
			.build();
	}
}
