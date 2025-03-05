package com.example.idolverse.domain.account.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.idolverse.domain.account.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
