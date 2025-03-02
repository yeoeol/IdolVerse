package com.example.idolverse.global.common.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.idolverse.domain.member.repository.MemberRepository;
import com.example.idolverse.global.common.entity.CustomMemberDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email)
			.map(member -> new CustomMemberDetails(member))
			.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}
}
