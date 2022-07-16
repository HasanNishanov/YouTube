package com.company.dto.attach;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttachDTO {

    private String id;
    private String originalName;
    private String extension;
    private Long size;
    private String path;
    private LocalDateTime createdDate;
    private String url;

    public AttachDTO() {
    }

    public AttachDTO(String id, String url) {
        this.id = id;
        this.url = url;
    }
}
