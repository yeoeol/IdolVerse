package com.example.idolverse.domain.comment.dto;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;

public record PostParentDto(
        Long postId,
        String plainBody,
        CommunityMemberInfoDto author,
        String type
) {
}
