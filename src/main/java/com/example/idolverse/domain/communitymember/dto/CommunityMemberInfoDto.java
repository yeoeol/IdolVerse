package com.example.idolverse.domain.communitymember.dto;

import java.time.LocalDateTime;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.member.entity.Member;

import lombok.Builder;
import lombok.Data;

@Builder
public record CommunityMemberInfoDto(
	Long memberId,
	Long communityId,
	Boolean joined,
	String profileName,
	LocalDateTime firstJoinAt,
	// AvailableActionType availableActions,
	FollowCount followCount,
	Boolean hasMembership,
	Boolean hasOfficialMark,
	LocalDateTime joinedDate
) {

	public static CommunityMemberInfoDto from(Community community, Member member) {
		return CommunityMemberInfoDto.builder()
			.memberId(member.getMemberId())
			.communityId(community.getCommunityId())
			.joined(true)
			.profileName(member.getProfileName())
			.firstJoinAt(LocalDateTime.now())
			.followCount(new FollowCount(member.getFollowingCount(), member.getFollowerCount()))
			.hasMembership(member.getHasMembership())
			.hasOfficialMark(member.getHasOfficialMark())
			.joinedDate(member.getCreatedAt())
			.build();
	}

	public static CommunityMemberInfoDto from(CommunityMember communityMember) {
		return CommunityMemberInfoDto.builder()
			.memberId(communityMember.getMember().getMemberId())
			.communityId(communityMember.getCommunity().getCommunityId())
			.joined(true)
			.profileName(communityMember.getProfileName())
			.firstJoinAt(LocalDateTime.now())
			.followCount(new FollowCount(0L, 0L))
			.hasMembership(false)
			.hasOfficialMark(false)
			.joinedDate(communityMember.getMember().getCreatedAt())
			.build();
	}

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
