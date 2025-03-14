package com.example.idolverse.domain.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.community.service.CommunityService;
import com.example.idolverse.domain.communitymember.service.CommunityMemberService;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.post.dto.FeedTabPostsInfoDto;
import com.example.idolverse.domain.post.dto.PostsInfoDto;
import com.example.idolverse.domain.post.dto.PostResponseDto;
import com.example.idolverse.domain.post.entity.Post;
import com.example.idolverse.domain.post.repository.PostRepository;
import com.example.idolverse.global.common.entity.CustomMemberDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
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

	public FeedTabPostsInfoDto getAllPosts(String urlPath) {
		Community community = communityService.findByUrlPath(urlPath);
		List<Post> posts = postRepository.findPostsByCommunity(community);

		List<PostResponseDto> postsInfoDtos = posts.stream()
			.map(post -> PostResponseDto.from(post))
			.toList();

		PostsInfoDto postsInfoDto = PostsInfoDto.from(postsInfoDtos);

		return FeedTabPostsInfoDto.from(postsInfoDto);
	}
}
