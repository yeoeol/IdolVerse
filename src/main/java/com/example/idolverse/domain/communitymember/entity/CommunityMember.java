package com.example.idolverse.domain.communitymember.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.communitymember.entity.enums.ProfileType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "community_id")
	private Community community;

	@Builder.Default // 이후 회원 정보 수정
	private String profileName = UUID.randomUUID().toString();

	@Builder.Default
	private String profileImageUrl = "anonymous.jpg";
	@Builder.Default
	private String profileComment = "";

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private ProfileType profileType = ProfileType.FAN;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private Long followingCount;
	private Long followerCount;

	@Builder.Default
	private Boolean hasMembership = false;
	@Builder.Default
	private Boolean hasOfficialMark = false;

	private LocalDateTime joinedAt;
}
