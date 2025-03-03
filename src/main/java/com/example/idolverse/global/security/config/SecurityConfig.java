package com.example.idolverse.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import com.example.idolverse.global.common.service.CustomUserDetailsService;
import com.example.idolverse.global.security.jwt.JwtAuthenticationFilter;
import com.example.idolverse.global.security.jwt.JwtProperties;
import com.example.idolverse.global.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtProperties jwtProperties;
	private final JwtProvider jwtProvider;
	private final AntPathMatcher pathMatcher;

	public static final String[] permitURI = {
		"/swagger-ui/index.html", "/swagger-ui/**", "/v3/api-docs/**",
		"/api/v1/accounts/**",
		"/redisTest"
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
					"/api/v1/members/**",
					"/api/v1/communities/**"
				).hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.userDetailsService(customUserDetailsService)
			.formLogin(login -> login
				.usernameParameter("email")
			);

		http
			.addFilterBefore(new JwtAuthenticationFilter(jwtProperties, jwtProvider, pathMatcher),
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
