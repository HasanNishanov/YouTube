package com.company.dto.playlist;

import com.company.dto.VideoPlaylistInfoDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.video.VideoDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ChannelEntity;
import com.company.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PlaylistDTO {

    private String uuid;
    private String name;
    private ChannelDTO channel;
    private Integer order;
    private PlaylistStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String attachUrl;
    private Integer videoCount;

    private List<VideoDTO> videoList;

    private List<VideoPlaylistInfoDTO> videoPlaylistInfo;
}
