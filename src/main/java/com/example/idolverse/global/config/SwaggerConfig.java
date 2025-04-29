package com.example.idolverse.global.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(getInfo())
			.components(new Components().addSecuritySchemes(
				"BearerAuth", getSecurityScheme()
			))
			.addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
			.group("IdolVerse")
			.packagesToScan("com.example.idolverse.domain")
			.build();
	}

	private Info getInfo() {
		return new Info()
			.title("IdolVerse Application API Docs")
			.description("JWT 기반 인증이 포함된 Swagger 설정")
			.version("1.0");
	}

	private SecurityScheme getSecurityScheme() {
		return new SecurityScheme()
			.name("Authorization")
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT");
	}
}
