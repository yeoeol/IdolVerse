package com.example.idolverse.domain.post.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record PostsInfoDto(
	List<PostResponseDto> data
) {
	public static PostsInfoDto from(List<PostResponseDto> postsInfoDtos) {
		return PostsInfoDto.builder()
			.data(postsInfoDtos)
			.build();
	}
}
