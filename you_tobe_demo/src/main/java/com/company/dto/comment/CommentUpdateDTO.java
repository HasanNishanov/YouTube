package com.company.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateDTO {

    private String articleId;
    private String content;

}
