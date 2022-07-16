package com.company.dto.video;

import com.company.dto.profile.ProfileDTO;
import com.company.enums.VideoLikeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoLikeDTO {

    private Integer id;
    private LocalDateTime createdDate;
    private ProfileDTO profileDTO;
    private VideoDTO videoDTO;
    private VideoLikeStatus status;
    private Boolean visible;
}
