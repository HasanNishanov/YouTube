package com.company.dto.channel;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChannelCreateDTO {

    @NotBlank
    private String name;
    private String attach;
    private String banner;
    private String websiteUrl;
    private String telegramUrl;
    private String instagramUrl;

}
