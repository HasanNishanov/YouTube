package com.company.service;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.VideoPlaylistInfoDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.playlist.PlaylistCreatedDTO;
import com.company.dto.playlist.PlaylistDTO;
import com.company.dto.playlist.PlaylistUpdateDTO;
import com.company.entity.AttachEntity;
import com.company.entity.PlaylistEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.exp.NotPermissionException;
import com.company.mapper.CustomPlaylistRepository;
import com.company.mapper.PlaylistShortInfo;
import com.company.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private VideoService videoService;


    public PlaylistDTO created(PlaylistCreatedDTO dto) {

        PlaylistEntity entity = new PlaylistEntity();
        entity.setName(dto.getName());
        entity.setOrder(dto.getOrder());
        entity.setAttachId(dto.getAttachId());
        entity.setChannelId(dto.getChannelId());

        playlistRepository.save(entity);

        return getDTO(entity);
    }

    private PlaylistDTO getDTO(PlaylistEntity entity) {

        PlaylistDTO dto = new PlaylistDTO();
        dto.setName(entity.getName());
        dto.setOrder(entity.getOrder());
        dto.setChannel(channelService.getById(entity.getChannelId()));
        dto.setAttachUrl(attachService.getAttachOpenUrl(entity.getAttachId()));
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setVisible(entity.getVisible());
        dto.setUuid(entity.getUuid());

        return dto;
    }

    public PlaylistDTO update(PlaylistUpdateDTO dto, String playlistId) {

        PlaylistEntity entity = get(playlistId);
        if (!entity.getChannel().getProfileId().equals(profileService.getProfile().getId())) {
            throw new BadRequestException("No access");
        }

        entity.setName(dto.getName());
        entity.setOrder(dto.getOrder());
        entity.setStatus(dto.getStatus());

        AttachEntity oldPhoto = entity.getAttach();
        if (entity.getAttachId() == null && dto.getAttachId() != null) {
            entity.setAttachId(dto.getAttachId());
        } else if (entity.getAttachId() != null && dto.getAttachId() != null) {
            entity.setAttachId(dto.getAttachId());
        } else if (entity.getAttachId() != null && dto.getAttachId() == null) {
            entity.setAttachId(null);
        }

        if (oldPhoto != null) {
            attachService.deleted(oldPhoto.getUuid());
        }

        return getDTO(entity);
    }

    private PlaylistEntity get(String playlistId) {

        return playlistRepository.findById(playlistId).orElseThrow(() -> {
            throw new ItemNotFoundException("palylist not fount");
        });
    }

    public List<PlaylistDTO> getProfilePLayList() {
        List<PlaylistDTO> playlist = new LinkedList<>();

        ProfileEntity profileEntity = profileService.getProfile();
        List<PlaylistShortInfo> entityList = playlistRepository.getProfilePlayLists(profileEntity.getId());

        for (PlaylistShortInfo entity : entityList) {
            PlaylistDTO dto = new PlaylistDTO();
            dto.setUuid(entity.getPlaylistId());
            dto.setName(entity.getPlaylistName());
            dto.setCreatedDate(entity.getPlaylistCreatedDate());
            dto.setChannel(new ChannelDTO(entity.getChannelId(), entity.getChannelName()));
            dto.setVideoCount(entity.getCountVideo());

            List<VideoPlaylistInfoDTO> videoPlaylistInfoList = videoService.getPlaylistFirstTwoVideo(entity.getPlaylistId());
            dto.setVideoPlaylistInfo(videoPlaylistInfoList);

            playlist.add(dto);
        }
        return playlist;
    }

    public List<PlaylistDTO> getChannelPLayList(String channelId) {
        List<PlaylistDTO> playlist = new LinkedList<>();

        List<PlaylistShortInfo> entityList = playlistRepository.getChannelPlayLists(channelId);

        for (PlaylistShortInfo entity : entityList) {
            PlaylistDTO dto = new PlaylistDTO();
            dto.setUuid(entity.getPlaylistId());
            dto.setName(entity.getPlaylistName());
            dto.setCreatedDate(entity.getPlaylistCreatedDate());
            dto.setChannel(new ChannelDTO(entity.getChannelId(), entity.getChannelName()));
            dto.setVideoCount(entity.getCountVideo());

            List<VideoPlaylistInfoDTO> videoPlaylistInfoList = videoService.getPlaylistFirstTwoVideo(entity.getPlaylistId());
            dto.setVideoPlaylistInfo(videoPlaylistInfoList);

            playlist.add(dto);
        }
        return playlist;
    }

    public PlaylistDTO getPlayaListById(String playlistId) {
//     id,name,video_count,last_update_date,  total_view_count (shu play listdagi videolarni ko'rilganlar soni),
//
        /* Optional<CustomPlaylistRepository> optional = playlistRepository.getPlaylistById(playlistId);*/

       /* PlaylistShortInfo playlistShortInfo = playlistRepository.getPlaylistShortInfo(playlistId);
        Integer totalWatchedCount = playlistRepository.getTotalWatchedVideoCount(playlistId);
        PlaylistDTO dto = new PlaylistDTO();*/

        PlaylistShortInfo playlistShortInfo = playlistRepository.getPlaylistShortInfoWithTotalWatchedCount(playlistId);
        return null;
    }

    public ResponseInfoDTO changeVisible(String pId) {

        ProfileEntity profile = profileService.getProfile();
        PlaylistEntity playlist = get(pId);
        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN) &&
                !playlist.getChannel().getProfileId().equals(profile.getId())) {
            throw new NotPermissionException("No access");
        }

        playlist.setVisible(!playlist.getVisible());
        playlistRepository.save(playlist);

        return new ResponseInfoDTO(1, "success");
    }


    public List<PlaylistDTO> pagination(Integer page, Integer size) {

        List<PlaylistEntity> pagination = playlistRepository.pagination(size, page * size);

        List<PlaylistDTO> playlistDTO = new ArrayList<>();
        pagination.forEach(playlist -> {
            playlistDTO.add(getDTO(playlist));
        });

        return playlistDTO;
    }
}
