package com.example.idolverse.global.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final JwtProperties jwtProperties;
	private final SecretKey signingKey;

	public JwtProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.signingKey = Keys.hmacShaKeyFor(
			Decoders.BASE64URL.decode(jwtProperties.getSecret())
		);
	}

	// 액세스 토큰 생성
	public String generateAccessToken(Long memberId) {
		long now = System.currentTimeMillis();
		return Jwts.builder()
			.subject(String.valueOf(memberId))
			.issuedAt(new Date(now))
			.expiration(new Date(now + jwtProperties.getAccessToken().getExpiration()))
			.signWith(signingKey)
			.compact();
	}

	// 리프레시 토큰 생성
	public String generateRefreshToken() {
		long now = System.currentTimeMillis();
		return Jwts.builder()
			.issuedAt(new Date(now))
			.expiration(new Date(now + jwtProperties.getRefreshToken().getExpiration()))
			.signWith(signingKey)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (ExpiredJwtException e) {
			return false;
		} catch (JwtException e) {
			return false;
		}
	}
}
