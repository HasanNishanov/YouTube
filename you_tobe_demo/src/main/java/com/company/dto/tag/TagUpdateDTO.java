package com.company.dto.tag;

import com.company.enums.TagStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagUpdateDTO {

    private String name;
    private TagStatus status;
}
