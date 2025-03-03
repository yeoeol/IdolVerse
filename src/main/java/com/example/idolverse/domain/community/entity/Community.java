package com.example.idolverse.domain.community.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Community extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_id")
	private Long communityId;

	private String communityName;

	private String urlPath;

	private Long memberCount;

	private String fandomName;

	private String artistCode;

	@OneToMany(mappedBy = "community")
	private List<CommunityMember> members = new ArrayList<>();
	// @Getter
	// @Builder
	// public static class ArtistOfficialNames {
	// 	private List<String> data;
	// 	private Long totalCount;
	// }
}
