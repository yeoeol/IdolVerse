package com.example.idolverse.domain.comment.controller;

import com.example.idolverse.domain.comment.dto.CommentRequestDto;
import com.example.idolverse.domain.comment.dto.CommentResponseDto;
import com.example.idolverse.domain.comment.service.CommentService;
import com.example.idolverse.global.response.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment-Controller", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "게시글 댓글 작성")
    @PostMapping("/{urlPath}/fanpost/{postId}/comment")
    public ApiResponseDto<CommentResponseDto> writeComment(
            @PathVariable String urlPath,
            @PathVariable Long postId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        CommentResponseDto commentResponseDto = commentService.writeComment(postId, commentRequestDto);
        return ApiResponseDto.success(commentResponseDto);
    }
}
