package com.example.idolverse.global.common.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.idolverse.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class CustomMemberDetails implements UserDetails, OAuth2User {

	private final Member member;

	public CustomMemberDetails(Member member) {
		this.member = member;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@Override
	public String getName() {
		return member.getEmail();
	}

	public Long getMemberId() {
		return member.getMemberId();
	}
}
