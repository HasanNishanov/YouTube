package com.company.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {

    private String videoId;
    private Integer commentId;
    private String content;

}
