package com.example.idolverse.domain.comment.entity;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.post.entity.Post;
import com.example.idolverse.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private String body;

    @ManyToOne
    @JoinColumn(name = "community_member_id")
    private CommunityMember communityMember;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // 연관관계 설정용 내부 메서드
    public void setPost(Post post) {
        this.post = post;
    }
}
