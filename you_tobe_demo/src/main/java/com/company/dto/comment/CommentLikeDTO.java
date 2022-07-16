package com.company.dto.comment;

import com.company.dto.profile.ProfileDTO;
import com.company.enums.CommentLikeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentLikeDTO {

    private Integer id;
    private LocalDateTime createdDate;
    private ProfileDTO profileDTO;
    private CommentDTO commentDTO;
    private CommentLikeStatus status;

}
