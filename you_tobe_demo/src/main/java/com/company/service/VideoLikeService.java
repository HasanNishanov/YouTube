package com.company.service;

import com.company.entity.ProfileEntity;
import com.company.entity.VideoLikeEntity;
import com.company.enums.LikeStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.VideoLikeRepository;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class VideoLikeService {
    @Autowired
    private VideoLikeRepository videoLikeRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ProfileService profileService;

    public void videoLike(String videoId) {
        ProfileEntity profile = profileService.getProfile();
        likeDislike(videoId, profile.getId(), LikeStatus.LIKE);
    }

    public void videoDisLike(String videoId) {
        ProfileEntity profile = profileService.getProfile();
        likeDislike(videoId, profile.getId(), LikeStatus.DISLIKE);
    }

    private void likeDislike(String videoId, Integer pId, LikeStatus status) {
        Optional<VideoLikeEntity> optional = videoLikeRepository.findExists(videoId, pId);
        if (optional.isPresent()) {
            VideoLikeEntity like = optional.get();
            like.setStatus(status);
            videoLikeRepository.save(like);
            return;
        }
        boolean articleExists = videoRepository.existsById(videoId);
        if (!articleExists) {
            throw new ItemNotFoundException("Video NotFound");
        }

        VideoLikeEntity like = new VideoLikeEntity();
        like.setVideoId(videoId);
        like.setProfileId(pId);
        like.setStatus(status);
        videoLikeRepository.save(like);
    }

    public void removeLike(String videoId) {
       /* Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(videoId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleLikeRepository.delete(articleLikeEntity);
        });*/
        ProfileEntity profile = profileService.getProfile();
        videoLikeRepository.delete(videoId, profile.getId());
    }

    public Map<String, Integer> countLikeDislike(String videoId){

        return videoLikeRepository.countLikeDislike(videoId);
    }
}
