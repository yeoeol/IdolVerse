package com.example.idolverse.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import com.example.idolverse.global.common.service.CustomUserDetailsService;
import com.example.idolverse.global.oauth2.handler.OAuth2LoginSuccessHandler;
import com.example.idolverse.global.oauth2.service.CustomOAuth2MemberService;
import com.example.idolverse.global.redis.BlackListService;
import com.example.idolverse.global.security.jwt.JwtAuthenticationFilter;
import com.example.idolverse.global.security.jwt.JwtProperties;
import com.example.idolverse.global.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final CustomOAuth2MemberService customOAuth2MemberService;
	private final OAuth2LoginSuccessHandler OAuth2LoginSuccessHandler;
	private final BlackListService blackListService;
	private final JwtProperties jwtProperties;
	private final JwtProvider jwtProvider;
	private final AntPathMatcher pathMatcher;

	public static final String[] permitURI = {
		"/swagger-ui/index.html", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml",
		"/api/v1/accounts/register", "/api/v1/accounts/login", "/api/v1/accounts/refresh",
		"/redisTest",
		"/hc", "/env"
	};

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(permitURI).permitAll()
				.requestMatchers(
					"/api/v1/communities/**"
				).hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.userDetailsService(customUserDetailsService)
			.formLogin(login -> login
				.usernameParameter("email")
			);

		http
			.addFilterBefore(new JwtAuthenticationFilter(blackListService, jwtProperties, jwtProvider, pathMatcher),
				UsernamePasswordAuthenticationFilter.class);

		http
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(endpoint -> endpoint
					.userService(customOAuth2MemberService))
				.successHandler(OAuth2LoginSuccessHandler));

		//세션 설정 : STATELESS
		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
