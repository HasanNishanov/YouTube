package com.company.dto.comment;

import com.company.dto.profile.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {

    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private ProfileDTO profile;
//    private ArticleDTO article;
    private CommentDTO comment;
    private String content;
    private Boolean visible;

}
