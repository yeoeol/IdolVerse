package com.example.idolverse.domain.comment.dto;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponseDto(
        String body,
        LocalDateTime createdAt,
        int emotionCount,
        int commentCount,
        Long commentId,
        PostParentDto parent,
        CommunityMemberInfoDto author
) {
    public static CommentResponseDto of(
            String body,
            LocalDateTime createdAt,
            int emotionCount,
            int commentCount,
            Long commentId,
            PostParentDto parent,
            CommunityMemberInfoDto author) {

        return CommentResponseDto.builder()
                .body(body)
                .createdAt(createdAt)
                .emotionCount(emotionCount)
                .commentCount(commentCount)
                .commentId(commentId)
                .parent(parent)
                .author(author)
                .build();
    }
}
