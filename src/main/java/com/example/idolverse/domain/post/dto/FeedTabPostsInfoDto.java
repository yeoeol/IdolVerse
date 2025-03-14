package com.example.idolverse.domain.post.dto;

import lombok.Builder;

@Builder
public record FeedTabPostsInfoDto(
	PostsInfoDto feedTabPosts
) {

	public static FeedTabPostsInfoDto from(PostsInfoDto postsInfoDto) {
		return FeedTabPostsInfoDto.builder()
			.feedTabPosts(postsInfoDto)
			.build();
	}
}
