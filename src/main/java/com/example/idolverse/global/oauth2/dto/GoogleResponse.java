package com.example.idolverse.global.oauth2.dto;

import java.util.Map;

import com.example.idolverse.domain.member.entity.Member;

public class GoogleResponse {

	private final Map<String, Object> attribute;

	public GoogleResponse(Map<String, Object> attribute) {
		this.attribute = attribute;
	}

	public String getProvider() {
		return "google";
	}

	public String getProviderId() {
		return attribute.get("sub").toString();
	}

	public String getEmail() {
		return attribute.get("email").toString();
	}

	public String getName() {
		return attribute.get("name").toString();
	}

	public Member toEntity() {
		return Member.builder()
				.email(getEmail())
				.userKey(getName())
				.password("")
				.build();
	}
}
