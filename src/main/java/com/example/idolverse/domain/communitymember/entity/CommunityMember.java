package com.example.idolverse.domain.communitymember.entity;

import java.time.LocalDateTime;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.member.entity.Member;

import jakarta.persistence.Entity;
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

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private LocalDateTime joinedAt;
}
