package com.company.mapper;

import java.time.LocalDateTime;

public interface CustomPlaylistRepository {
    String getPlaylistId();
    String getPlaylistName();
    LocalDateTime getPlaylistUpdatedDate();

    Integer getVideoCount();
    Integer getTotalViewCount();
}