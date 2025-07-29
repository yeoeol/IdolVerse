package com.example.idolverse.domain.comment.dto;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;

import java.time.LocalDateTime;

public record CommentResponseDto(
        String body,
        LocalDateTime createdAt,
        int emotionCount,
        int commentCount,
        Long commentId,
        PostParentDto parent,
        CommunityMemberInfoDto author
) {
}
