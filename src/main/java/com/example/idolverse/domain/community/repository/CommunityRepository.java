package com.example.idolverse.domain.community.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.idolverse.domain.community.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {

	Optional<Community> findByUrlPath(String urlPath);
}
