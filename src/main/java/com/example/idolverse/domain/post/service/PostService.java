package com.example.idolverse.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.community.service.CommunityService;
import com.example.idolverse.domain.communitymember.service.CommunityMemberService;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.post.dto.PostResponseDto;
import com.example.idolverse.domain.post.entity.Post;
import com.example.idolverse.domain.post.repository.PostRepository;
import com.example.idolverse.global.common.entity.CustomMemberDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final CommunityService communityService;
	private final CommunityMemberService communityMemberService;

	@Transactional
	public PostResponseDto post(String urlPath, String content, CustomMemberDetails customMemberDetails) {
		Community community = communityService.findByUrlPath(urlPath);
		Member member = customMemberDetails.getMember();
		communityMemberService.existsMemberInCommunity(community, member);
		Post post = Post.builder()
			.content(content)
			.member(customMemberDetails.getMember())
			.community(community)
			.build();

		Post savedPost = postRepository.save(post);
		return PostResponseDto.from(savedPost);
	}
}
