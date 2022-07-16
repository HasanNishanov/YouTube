package com.company.dto.video;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class VideoCreatedDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String attach;

    private String review;
    @NotBlank
    private String channelId;

    private String playlist;

    private Integer categoryId;

    private List<String> tags;

}
