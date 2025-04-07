package com.example.idolverse.global.security.jwt;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.idolverse.global.config.SecurityConfig;
import com.example.idolverse.domain.account.service.BlackListService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final BlackListService blackListService;
	private final JwtProperties jwtProperties;
	private final JwtProvider jwtProvider;
	private final AntPathMatcher pathMatcher;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String requestURI = request.getRequestURI();
		return (Arrays.stream(SecurityConfig.permitURI).anyMatch(
			pattern -> pathMatcher.match(pattern, requestURI)
		));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		System.out.println("JwtAuthenticationFilter.doFilterInternal 호출");

		String authorizationHeader = request.getHeader(jwtProperties.getHeaderAuthorization());
		String token = jwtProvider.resolveHeader(authorizationHeader);
		try {
			if (jwtProvider.validateToken(token) && (blackListService.findBlackList(token) == null)) {
				Authentication authentication = jwtProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			return;
		}

		filterChain.doFilter(request, response);
	}
}
