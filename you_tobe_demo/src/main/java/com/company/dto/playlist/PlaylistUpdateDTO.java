package com.company.dto.playlist;

import com.company.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistUpdateDTO {

    private String name;
    private Integer order;
    private String attachId;
    private PlaylistStatus status;

}
