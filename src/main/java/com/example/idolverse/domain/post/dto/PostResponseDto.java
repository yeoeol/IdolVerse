package com.example.idolverse.domain.post.dto;

import com.example.idolverse.domain.post.entity.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "게시글 작성 응답 DTO")
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
