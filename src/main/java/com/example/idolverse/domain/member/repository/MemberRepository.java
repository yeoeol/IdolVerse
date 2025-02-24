package com.example.idolverse.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.idolverse.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
