package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmail(String email);

    Page<ProfileEntity> findAllByVisible(Boolean b, Pageable pageable);
}
