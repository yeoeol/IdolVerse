package com.example.idolverse.domain.cheer.entity;

import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Cheer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cheer_id")
    private Long cheerId;

    @ManyToOne
    @JoinColumn(name = "community_member_id")
    private CommunityMember communityMember;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public static Cheer create(CommunityMember communityMember, Post post) {
        Cheer cheer = new Cheer();
        cheer.communityMember = communityMember;
        cheer.post = post;
        return cheer;
    }
}
