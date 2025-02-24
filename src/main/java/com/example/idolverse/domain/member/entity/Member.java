package com.example.idolverse.domain.member.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.member.entity.enums.MemberRole;
import com.example.idolverse.domain.member.entity.enums.ProfileType;
import com.example.idolverse.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	private String email;

	@Builder.Default // 이후 회원 정보 수정
	private String nickname = UUID.randomUUID().toString();
	private String password;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private MemberRole role = MemberRole.USER;

	@Builder.Default // 이후 회원 정보 수정
	private String profileName = UUID.randomUUID().toString();;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private ProfileType profileType = ProfileType.FAN;

	private Boolean hasMembership;
	private Boolean hasOfficialMark;

	@OneToMany(mappedBy = "member")
	private List<CommunityMember> communities = new ArrayList<>();
}
