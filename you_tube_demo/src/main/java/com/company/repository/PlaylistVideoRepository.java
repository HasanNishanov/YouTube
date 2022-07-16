package com.company.repository;

import com.company.entity.PlaylistVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideoEntity, String> {
}
