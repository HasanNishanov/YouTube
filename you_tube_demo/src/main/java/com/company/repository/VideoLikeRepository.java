package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.entity.VideoEntity;
import com.company.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity, Integer> {

    Optional<VideoLikeEntity> findByVideoAndProfile(VideoEntity video, ProfileEntity profile);

    @Query("FROM VideoLikeEntity a where  a.video.uuid =:articleId and a.profile.id =:profileId")
    Optional<VideoLikeEntity> findExists(String articleId, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VideoLikeEntity a where  a.video.uuid =:articleId and a.profile.id =:profileId")
    void delete(String articleId, Integer profileId);


    //    @Transactional
//    @Modifying
    @Query(value = "\n" +
            "select cast(sum(case when status = 'LIKE' then 1 else 0 end) as int) as like_count, " +
            "       cast(sum(case when status = 'DISLIKE' then 1 else 0 end) as int) as dislike_count " +
            "from video_like " +
            "where video_like.video_id =:videoId", nativeQuery = true)
    Map<String, Integer> countLikeDislike(@Param("videoId") String videoId);
}
