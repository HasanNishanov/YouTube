package com.company.dto.channel;

import com.company.dto.profile.ProfileShortDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChannelStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public interface ChannelShortDTO {

    String getUuid();
    String getName();
    String getPhoto();
    Integer getPId();
    ChannelStatus getStatus();
    Boolean getVisible();
    LocalDateTime getCreatedDate();



//    private String uuid;
//    private String name;
//    private String attach_id;
//    private Integer profile_id;
//    private ChannelStatus status;
//    private Boolean visible;
//    private LocalDateTime created_date;
}
