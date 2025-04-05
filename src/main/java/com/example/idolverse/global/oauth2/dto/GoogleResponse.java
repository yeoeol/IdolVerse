package com.example.idolverse.global.oauth2.dto;

import java.util.Map;

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
}
