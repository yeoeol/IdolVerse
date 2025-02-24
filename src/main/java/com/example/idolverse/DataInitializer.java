package com.example.idolverse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.entity.enums.MemberRole;
import com.example.idolverse.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (memberRepository.count() == 0) {
			Member member1 = Member.builder()
				.email("member1@naver.com")
				.password(passwordEncoder.encode("11111"))
				.build();
			Member member2 = Member.builder()
				.email("member2@naver.com")
				.password(passwordEncoder.encode("22222"))
				.build();
			Member admin = Member.builder()
				.email("admin@naver.com")
				.password(passwordEncoder.encode("33333"))
				.role(MemberRole.ADMIN)
				.build();
			memberRepository.save(member1);
			memberRepository.save(member2);
			memberRepository.save(admin);
		}
	}
}
