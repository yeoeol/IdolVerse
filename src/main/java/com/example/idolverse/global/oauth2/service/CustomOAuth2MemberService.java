package com.example.idolverse.global.oauth2.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.repository.MemberRepository;
import com.example.idolverse.global.common.entity.CustomMemberDetails;
import com.example.idolverse.global.oauth2.dto.GoogleResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		Map<String, Object> attributes = oAuth2User.getAttributes();
		/*
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			System.out.printf("테스트 : key = %s, value = %s\n", entry.getKey(), entry.getValue());
		} */

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		GoogleResponse googleResponse = null;
		if (registrationId.equals("google")) {
			googleResponse = new GoogleResponse(attributes);
		}
		else {
			throw new OAuth2AuthenticationException("Unsupported OAuth2 provider: " + registrationId);
		}

		Member member = saveOrUpdate(googleResponse);
		return new CustomMemberDetails(member);
	}

	private Member saveOrUpdate(GoogleResponse response) {
		Member member = memberRepository.findByEmail(response.getEmail())
				.map(m -> m.update(response.getName()))
				.orElse(response.toEntity());

		return memberRepository.save(member);
	}
}
