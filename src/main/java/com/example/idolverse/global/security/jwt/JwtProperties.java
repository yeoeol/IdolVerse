package com.example.idolverse.global.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private String secret;
	private AccessTokenProperties accessToken;
	private RefreshTokenProperties refreshToken;

	@Data
	public static class AccessTokenProperties {
		private long expiration;
	}

	@Data
	public static class RefreshTokenProperties {
		private long expiration;
	}
}
