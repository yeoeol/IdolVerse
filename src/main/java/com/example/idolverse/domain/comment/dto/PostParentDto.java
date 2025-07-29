package com.example.idolverse.domain.comment.dto;

import com.azure.core.http.HttpMethod;
import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.post.entity.Post;
import lombok.Builder;

@Builder
public record PostParentDto(
        Long postId,
        String plainBody,
        CommunityMemberInfoDto author,
        String type
) {
    public static PostParentDto from(Post post) {
        return PostParentDto.builder()
            .postId(post.getPostId())
            .plainBody(post.getPlainBody())
            .author(CommunityMemberInfoDto.from(post.getCommunityMember()))
            .type(HttpMethod.POST.name())
            .build();
    }
}
