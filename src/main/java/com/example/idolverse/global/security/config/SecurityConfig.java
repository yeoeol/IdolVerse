package com.example.idolverse.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/v1/members/**").hasRole("ADMIN")
				.requestMatchers("/api/v1/accounts/**").permitAll()
				.anyRequest().authenticated()
			)
			.userDetailsService(customUserDetailsService)
			.formLogin(login -> login
				.usernameParameter("email")
			);

		http
			.addFilterBefore(new JwtAuthenticationFilter(jwtProperties, jwtProvider), UsernamePasswordAuthenticationFilter.class);


		return http.build();
	}
}
