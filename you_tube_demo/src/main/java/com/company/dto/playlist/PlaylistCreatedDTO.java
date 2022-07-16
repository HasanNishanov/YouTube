package com.company.dto.playlist;

import com.company.entity.AttachEntity;
import com.company.entity.ChannelEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistCreatedDTO {

    private String name;
    private Integer order;
    private String channelId;
    private String attachId;

}
