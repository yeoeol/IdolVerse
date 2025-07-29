package com.example.idolverse.domain.comment.dto;

public record CommentRequestDto(
        Long communityMemberId,
        String body
) {
}
