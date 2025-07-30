package com.example.idolverse.domain.cheer.service;

import com.example.idolverse.domain.cheer.dto.CheerRequestDto;
import com.example.idolverse.domain.cheer.entity.Cheer;
import com.example.idolverse.domain.cheer.repository.CheerRepository;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.communitymember.service.CommunityMemberService;
import com.example.idolverse.domain.post.entity.Post;
import com.example.idolverse.domain.post.service.PostService;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheerService {

    private final CheerRepository cheerRepository;
    private final CommunityMemberService communityMemberService;
    private final PostService postService;

    @Transactional
    public void cheer(CheerRequestDto cheerRequestDto) {
        CommunityMember communityMember = communityMemberService.findById(cheerRequestDto.communityMemberId());
        Post post = postService.findById(cheerRequestDto.postId());

        // 응원 중복 체크
        if (cheerRepository.existsByCommunityMemberAndPost(communityMember, post)) {
            throw new GeneralException(ErrorCode.CHEER_ALREADY_EXISTS);
        }

        Cheer cheer = Cheer.create(communityMember, post);
        cheerRepository.save(cheer);
    }

    @Transactional
    public void cancelCheer(CheerRequestDto cheerRequestDto) {
        CommunityMember communityMember = communityMemberService.findById(cheerRequestDto.communityMemberId());
        Post post = postService.findById(cheerRequestDto.postId());

        if (cheerRepository.deleteByCommunityMemberAndPost(communityMember, post) == 0) {
            throw new GeneralException(ErrorCode.CHEER_NOT_FOUND);
        }
    }
}
