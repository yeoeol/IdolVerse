package com.example.idolverse.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

@Configuration
public class AppConfig {

	@Bean
	public AntPathMatcher pathMatcher() {
		return new AntPathMatcher();
	}
}
