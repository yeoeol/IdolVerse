package com.example.idolverse.domain.post.entity;

import com.example.idolverse.domain.cheer.entity.Cheer;
import com.example.idolverse.domain.comment.entity.Comment;
import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.global.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;

	private Boolean hideFromArtist;

	private String plainBody;

	@ManyToOne
	@JoinColumn(name = "community_member_id")
	private CommunityMember communityMember;

	@ManyToOne
	@JoinColumn(name = "community_id")
	private Community community;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cheer> cheers = new ArrayList<>();

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setPost(this);
	}
}
