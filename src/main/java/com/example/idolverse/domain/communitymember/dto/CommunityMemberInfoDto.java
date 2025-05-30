package com.example.idolverse.domain.communitymember.dto;

import java.time.LocalDateTime;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(title = "커뮤니티 내 회원 정보 DTO")
@Builder
public record CommunityMemberInfoDto(
	Long memberId,
	Long communityId,
	Boolean joined,
	String profileName,
	String profileImageUrl,
	String profileComment,
	String profileType,
	LocalDateTime firstJoinAt,
	// AvailableActionType availableActions,
	FollowCount followCount,
	Boolean hasMembership,
	Boolean hasOfficialMark,
	LocalDateTime joinedDate
) {

	public static CommunityMemberInfoDto from(CommunityMember communityMember) {
		return CommunityMemberInfoDto.builder()
			.memberId(communityMember.getMember().getMemberId())
			.communityId(communityMember.getCommunity().getCommunityId())
			.joined(true)
			.profileName(communityMember.getProfileName())
			.profileImageUrl(communityMember.getProfileImageUrl())
			.profileComment(communityMember.getProfileComment())
			.profileType(communityMember.getProfileType().name())
			.firstJoinAt(communityMember.getJoinedAt())
			.followCount(new FollowCount(communityMember.getFollowingCount(), communityMember.getFollowerCount()))
			.hasMembership(communityMember.getHasMembership())
			.hasOfficialMark(communityMember.getHasOfficialMark())
			.joinedDate(communityMember.getMember().getCreatedAt())
			.build();
	}

	@Schema(description = "특정 멤버에 대한 팔로잉/팔로우 수 DTO")
	@Data
	public static class FollowCount {
		private Long followingCount;
		private Long followerCount;

		public FollowCount(Long followingCount, Long followerCount) {
			this.followingCount = followingCount;
			this.followerCount = followerCount;
		}
	}
}
