package com.example.idolverse.domain.post.entity;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
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
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;

	private Boolean hideFromArtist;

	private String plainBody;

	@ManyToOne
	@JoinColumn(name = "community_member_id")
	private CommunityMember communityMember;

	@ManyToOne
	@JoinColumn(name = "community_id")
	private Community community;
}
