package com.company.dto.tag;

import com.company.enums.TagStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TagDTO {

    private Integer id;
    private String name;
    private TagStatus status;
    private LocalDateTime createdDate;
    Boolean visible;
}
