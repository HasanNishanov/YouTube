package com.company.dto.channel;

import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileShortDTO;
import com.company.enums.ChannelStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChannelShortInfoDTO {

    private String uuid;
    private String name;
    private String attachUrl;
    private ProfileShortDTO profile;
    private ChannelStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;
}
