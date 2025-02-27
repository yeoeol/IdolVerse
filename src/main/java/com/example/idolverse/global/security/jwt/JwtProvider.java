package com.example.idolverse.global.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.repository.MemberRepository;
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

	public JwtProvider(MemberRepository memberRepository, CustomUserDetailsService customUserDetailsService,
		JwtProperties jwtProperties) {
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
			getClaims(token);
			return true;
		} catch (ExpiredJwtException e) {
			return false;
		} catch (JwtException e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		String email = claims.getSubject();

		UserDetails principal = customUserDetailsService.loadUserByUsername(email);
		System.out.println("password = " + principal.getPassword());
		return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith(signingKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
