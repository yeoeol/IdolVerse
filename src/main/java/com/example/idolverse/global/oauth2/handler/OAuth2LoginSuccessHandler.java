package com.example.idolverse.global.oauth2.handler;

import java.io.IOException;
import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.idolverse.domain.account.service.RefreshTokenService;
import com.example.idolverse.global.common.entity.CustomMemberDetails;
import com.example.idolverse.global.security.jwt.JwtProperties;
import com.example.idolverse.global.security.jwt.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final RefreshTokenService refreshTokenService;
	private final JwtProperties jwtProperties;
	private final JwtProvider jwtProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		CustomMemberDetails principal = (CustomMemberDetails)authentication.getPrincipal();
		String email = principal.getName();

		String accessToken = jwtProvider.generateAccessToken(email);
		String refreshToken = jwtProvider.generateRefreshToken(email);

		refreshTokenService.saveRefreshToken(email, refreshToken);

		setAccessTokenHeader(response, accessToken);
		setHttpOnlyCookie(response, refreshToken);

		System.out.println("onAuthenticationSuccess 호출 완료");
		getRedirectStrategy().sendRedirect(request, response, "http://localhost:8080/swagger-ui/index.html#/");
	}

	private void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(jwtProperties.getHeaderAuthorization(), "Bearer " + accessToken);
	}

	private void setHttpOnlyCookie(HttpServletResponse response, String refreshToken) {
		long maxAgeSeconds = Duration.ofMillis(jwtProperties.getRefreshToken().getExpiration()).getSeconds();

		ResponseCookie refreshTokenCookie = ResponseCookie
				.from("refreshToken", refreshToken)
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(maxAgeSeconds)
				.build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
	}
}
