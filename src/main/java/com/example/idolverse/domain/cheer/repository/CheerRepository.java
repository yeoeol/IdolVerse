package com.example.idolverse.domain.cheer.repository;

import com.example.idolverse.domain.cheer.entity.Cheer;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheerRepository extends JpaRepository<Cheer, Long> {
    int deleteByCommunityMemberAndPost(CommunityMember communityMember, Post post);

    boolean existsByCommunityMemberAndPost(CommunityMember communityMember, Post post);
}
