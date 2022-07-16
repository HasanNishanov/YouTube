package com.company.dto.channel;

import com.company.dto.profile.ProfileDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChannelStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ChannelDTO {

    private String uuid;
    private String name;
    private String attachUrl;
    private String bannerUrl;
    private String websiteUrl;
    private String telegramUrl;
    private String instagramUrl;
    private ProfileDTO profile;
    private ChannelStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;

    public ChannelDTO() {
    }

    public ChannelDTO(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
