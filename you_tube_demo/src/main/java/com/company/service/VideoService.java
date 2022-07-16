package com.company.service;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.VideoPlaylistInfoDTO;
import com.company.dto.attach.AttachDTO;
import com.company.dto.video.VideoCreatedDTO;
import com.company.dto.video.VideoDTO;
import com.company.entity.*;
import com.company.mapper.VideoFullInfo;
import com.company.repository.VideoRepository;
import com.company.repository.VideoTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private PlaylistVideoService playlistVideoService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    public ResponseInfoDTO createdVideo(VideoCreatedDTO dto) {
        // set category id
        VideoEntity entity = new VideoEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setAttachId(dto.getAttach());

        if (dto.getChannelId() != null) {
            entity.setChannelId(dto.getChannelId());
        }

        if (dto.getReview() != null) {
            entity.setReviewId(dto.getReview());
        }

        entity.setChannelId(dto.getChannelId());

        videoRepository.save(entity);

        if (dto.getPlaylist() != null) {
            playlistVideoService.created(dto.getPlaylist(), entity.getUuid());
        }
        dto.getTags().forEach(tag -> {
            TagEntity tagEntity = tagService.createdIfNotExist(tag);
            VideoTagEntity videoTag = new VideoTagEntity();

            videoTag.setVideoId(entity.getUuid());
            videoTag.setTagId(tagEntity.getId());

            videoTagRepository.save(videoTag);
        });

        return new ResponseInfoDTO(1, "success");
    }

    public List<VideoPlaylistInfoDTO> getPlaylistFirstTwoVideo(String playlistId) {
        return videoRepository.getVideoFoPlayList(playlistId).stream().map(info -> {
            VideoPlaylistInfoDTO dto = new VideoPlaylistInfoDTO();
            dto.setPlaylistVideoId(info.getPlayListVideoId());
            dto.setVideoId(info.getVideoId());
            dto.setVideoName(info.getVideoName());
            return dto;
        }).collect(Collectors.toList());
    }

    /*   VideFullInfo
    (id,key,title,description, preview_attach(id,url),attach(id,url,duration),
       category(id,name), published_date, channel(id,name,photo(url))
       tagList(id,name), view_count,shared_count, Like(like_count,dislike_count)
       ,
       ,Like(like_count,dislike_count,
                                    isUserLiked,IsUserDisliked),duration)*/
    public List<VideoDTO> getVideoById(String videoId) {
        ProfileEntity entity = profileService.getProfile();
 /*       Optional<VideoEntity> optional = videoRepository.findById(videoId);
        // check

        VideoEntity video = optional.get();
        VideoDTO dto = new VideoDTO();

        dto.setReview(attachService.getAttachWithOpenUrl(video.getReviewId()));

        CategoryEntity category = video.getCategory();

        AttachEntity attach = video.getAttachId();
*/
        VideoFullInfo videoFullInfo = videoRepository.getVideFullIntoById(videoId, entity.getId());
        //  , tagList(id,name),

        return null;
    }
}
