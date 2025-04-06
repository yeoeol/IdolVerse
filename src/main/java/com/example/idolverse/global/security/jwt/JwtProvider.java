package com.example.idolverse.global.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.idolverse.global.common.service.CustomUserDetailsService;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtProperties jwtProperties;
	private final SecretKey signingKey;

	public JwtProvider(CustomUserDetailsService customUserDetailsService, JwtProperties jwtProperties) {
		this.customUserDetailsService = customUserDetailsService;
		this.jwtProperties = jwtProperties;
		this.signingKey = Keys.hmacShaKeyFor(
			Decoders.BASE64URL.decode(jwtProperties.getSecret())
		);
	}

	// 액세스 토큰 생성
	public String generateAccessToken(String email) {
		long now = System.currentTimeMillis();
		return Jwts.builder()
			.subject(email)
			.issuedAt(new Date(now))
			.expiration(new Date(now + jwtProperties.getAccessToken().getExpiration()))
			.signWith(signingKey)
			.compact();
	}

	// 리프레시 토큰 생성
	public String generateRefreshToken(String email) {
		long now = System.currentTimeMillis();
		return Jwts.builder()
			.subject(email)
			.issuedAt(new Date(now))
			.expiration(new Date(now + jwtProperties.getRefreshToken().getExpiration()))
			.signWith(signingKey)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (ExpiredJwtException e) {
			throw new GeneralException(ErrorCode.TOKEN_EXPIRED);
		} catch (JwtException e) {
			throw new GeneralException(ErrorCode.INVALID_TOKEN);
		}
	}

	public String resolveHeader(String authorizationHeader) {
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		String email = claims.getSubject();

		UserDetails principal = customUserDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
	}

	public String getSubject(String token) {
		Claims claims = getClaims(token);
		return claims.getSubject();
	}

	public long getExpirationTime(String token) {
		return getClaims(token).getExpiration().getTime();
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith(signingKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
