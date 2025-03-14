package com.example.idolverse.domain.post.dto;

import com.example.idolverse.domain.community.dto.CommunityDetailDto;
import com.example.idolverse.domain.community.dto.CommunityInfoDto;
import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.post.entity.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "게시글 작성 응답 DTO")
@Builder
public record PostResponseDto(
	Long postId,
	String plainBody,
	CommunityMemberInfoDto author,
	CommunityDetailDto community
) {
	public static PostResponseDto from(Post post) {
		return PostResponseDto.builder()
			.postId(post.getPostId())
			.plainBody(post.getPlainBody())
			.author(CommunityMemberInfoDto.from(post.getCommunityMember()))
			.community(CommunityDetailDto.from(post.getCommunity()))
			.build();
	}
}
