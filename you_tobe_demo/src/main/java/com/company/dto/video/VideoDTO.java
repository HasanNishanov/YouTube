package com.company.dto.video;

import com.company.dto.attach.AttachDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ChannelEntity;
import com.company.enums.VideoStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class VideoDTO {


    private String uuid;
    private String name;
    private String attachId;
    private AttachDTO attach;
    private String reviewId;
    private AttachDTO review;
    private String channelId;
    private ChannelDTO channel;
    private LocalDateTime createdDate;
    private Integer time;
    private Integer sharedCount;
    private Boolean visible;
    private VideoStatus status;


}
