package com.company.repository;

import com.company.entity.VideoEntity;
import com.company.entity.VideoTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoTagRepository extends JpaRepository<VideoTagEntity, Integer> {

    List<VideoTagEntity> findAllByVideo(VideoEntity video);

    @Query(value = "select v.tag.name from VideoTagEntity v where v.videoId =:videoId")
    List<String> getAllTagNameByVideoId(@Param("videoId") String videoId);
}
