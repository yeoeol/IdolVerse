package com.example.idolverse.global.oauth2.service;

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

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		GoogleResponse googleResponse = null;
		if (registrationId.equals("google")) {
			googleResponse = new GoogleResponse(oAuth2User.getAttributes());
		}
		else {
			return null;
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
