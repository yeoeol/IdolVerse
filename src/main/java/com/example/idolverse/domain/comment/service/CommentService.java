package com.example.idolverse.domain.comment.service;

import com.example.idolverse.domain.comment.dto.CommentRequestDto;
import com.example.idolverse.domain.comment.dto.CommentResponseDto;
import com.example.idolverse.domain.comment.dto.PostParentDto;
import com.example.idolverse.domain.comment.entity.Comment;
import com.example.idolverse.domain.comment.repository.CommentRepository;
import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.communitymember.service.CommunityMemberService;
import com.example.idolverse.domain.post.entity.Post;
import com.example.idolverse.domain.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final CommunityMemberService communityMemberService;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto writeComment(Long postId, CommentRequestDto requestDto) {
        CommunityMember communityMember = communityMemberService.findById(requestDto.communityMemberId());  // 댓글 작성자
        Post post = postService.findById(postId);

        Comment comment = Comment.create(requestDto.body(), communityMember, post);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.of(
            requestDto.body(),
            post.getCreatedAt(),
            0,
            0,
            savedComment.getCommentId(),
            PostParentDto.from(post),
            CommunityMemberInfoDto.from(communityMember)
        );
    }
}
