package com.example.idolverse.domain.communitymember.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.member.entity.Member;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
	boolean existsByCommunityAndMember(Community community, Member member);
}
