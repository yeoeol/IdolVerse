package com.example.idolverse.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "게시글 작성 요청 DTO")
public record PostRequestDto(
	Long communityMemberId,
	String plainBody,
	Boolean hideFromArtist
) {
}
