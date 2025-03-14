package com.example.idolverse.domain.communitymember.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "커뮤니티 내 회원 정보 수정 요청 DTO")
public record CommunityMemberUpdateRequestDto(
	String profileName,
	String profileComment
) {
}
