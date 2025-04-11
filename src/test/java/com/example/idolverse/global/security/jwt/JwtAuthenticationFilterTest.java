package com.example.idolverse.global.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.FilterChain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import com.example.idolverse.domain.account.service.BlackListService;

public class JwtAuthenticationFilterTest {

	@InjectMocks
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Mock
	private JwtProvider jwtProvider;

	@Mock
	private JwtProperties jwtProperties;

	@Mock
	private BlackListService blackListService;

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private FilterChain filterChain;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		SecurityContextHolder.clearContext();

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		filterChain = new MockFilterChain();

		// JwtProperties 모킹
		when(jwtProperties.getHeaderAuthorization()).thenReturn("Authorization");

		jwtAuthenticationFilter = new JwtAuthenticationFilter(
			blackListService,
			jwtProperties,
			jwtProvider,
			pathMatcher
		);
	}

	@DisplayName(value = "유효한 JWT 토큰이 주어졌을 때 인증(Authentication)이 정상적으로 설정되는지 검증")
	@Test
	public void whenValidJwtToken_thenSetsAuthentication() throws Exception {
		// given
		String validToken = "valid.jwt.token";
		String authorizationHeader = "Bearer " + validToken;
		request.addHeader("Authorization", authorizationHeader);

		when(jwtProvider.resolveHeader(authorizationHeader)).thenReturn(validToken);
		when(jwtProvider.validateToken(validToken)).thenReturn(true);
		when(blackListService.findBlackList(validToken)).thenReturn(null);

		UserDetails userDetails = new User("user", "password", new ArrayList<>());
		when(jwtProvider.getAuthentication(validToken)).thenReturn(
			new UsernamePasswordAuthenticationToken(userDetails, validToken, userDetails.getAuthorities())
		);

		// when
		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

		// then
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		assertThat(authentication).isNotNull();
		assertThat(authentication.getPrincipal()).isEqualTo(userDetails);
		verify(jwtProvider, times(1)).validateToken(validToken);
		verify(blackListService, times(1)).findBlackList(validToken);
	}

	@Test
	public void whenNoJwtToken_thenContinuesFilterChain() throws Exception {
		// given - no token

		// when
		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

		// then
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		assertThat(authentication).isNull();
	}
}
