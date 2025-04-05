package com.example.idolverse.global.oauth2.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.repository.MemberRepository;
import com.example.idolverse.global.common.entity.CustomMemberDetails;
import com.example.idolverse.global.oauth2.dto.GoogleResponse;

import lombok.RequiredArgsConstructor;

@Service
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

		String name = googleResponse.getName();
		String socialEmail = googleResponse.getEmail();
		Member member = memberRepository.findByEmail(socialEmail)
				.orElseGet(() -> saveSocialMember(socialEmail, name));

		return new CustomMemberDetails(member);
	}

	private Member saveSocialMember(String email, String name) {
		Member member = Member.builder()
				.email(email)
				.userKey(name)
				.password("")
				.build();
		return memberRepository.save(member);
	}
}
