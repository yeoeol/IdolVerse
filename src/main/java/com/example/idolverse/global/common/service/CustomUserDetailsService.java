package com.example.idolverse.global.common.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email)
			.map(member -> new User(member.getEmail(), member.getPassword(), getAuthorities(member)))
			.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Member member) {
		return List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
	}
}
